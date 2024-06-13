package com.example.userservicefinal.services;

import com.example.userservicefinal.models.Role;
import com.example.userservicefinal.models.Session;
import com.example.userservicefinal.repositories.SessionRepository;
import com.example.userservicefinal.response.ApiEntity;
import com.example.userservicefinal.response.ApiResponseObject;
import com.sun.net.httpserver.Headers;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import com.example.userservicefinal.dtos.UserDTO;
import com.example.userservicefinal.models.SessionStatus;
import com.example.userservicefinal.models.User;
import com.example.userservicefinal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  SessionRepository sessionRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;    //This will throw error as we have @Autowired it. Because BCryptPasswordEncoder class do not having any special annotation(like @Component, @Service, @Repository , @Entity) by Spring. So, we need to declare @Bean in ConfigFile in SpringSecurity class

    @Override
    public ResponseEntity<ApiResponseObject> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            String message= "User doesn't exist";
            return new ResponseEntity<ApiResponseObject>( new ApiEntity<>(message), HttpStatus.OK);
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            String message= "Password Incorrect";
            return new ResponseEntity<ApiResponseObject>( new ApiEntity<>(message), HttpStatus.OK);
        }
        //it will create the random token
        //String token = RandomStringUtils.randomAlphanumeric(30);

        //Now lets use the JWT token
        MacAlgorithm alg = Jwts.SIG.HS256; //or HS384 or HS256
        SecretKey key = alg.key().build();

        //In place of this we can take the JSON  ... here for simplicity we are taking Map
        Map<String, Object> jsonForJwt = new HashMap<>();
        jsonForJwt.put("email", user.getEmail());
        jsonForJwt.put("roles", user.getRoles());
        jsonForJwt.put("createdAt", new Date());
        jsonForJwt.put("expiryAt", new Date(LocalDate.now().plusDays(3).toEpochDay()));  //3 days from today expire

        String token = Jwts.builder()
                .claims(jsonForJwt)       //Converting Map to JSON for JWT payload
                .signWith(key, alg)
                .compact();

//        compact// Parse the compact JWS:
//        String content = Jwts.parser().verifyWith(key).build().parseSignedContent(jws).getPayload();

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);
        String message = "Successfully Logged In";
        ResponseEntity<ApiResponseObject> response = new ResponseEntity<ApiResponseObject>(new ApiEntity<UserDTO>(message, userDTO), headers, HttpStatus.OK);
        return response;
    }

    @Override
    public UserDTO signup(String username, String password) {
        User user = new User();
        user.setEmail(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));    //Storing encrypted password

        User savedUser = userRepository.save(user);
        return UserDTO.from(savedUser);
    }

    @Override
    public String logout(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if(sessionOptional.isEmpty()){
            return "Session is Ended!! Login again";
        }
        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
        return "Successfully Logged Out";
    }

    @Override
    public SessionStatus validate(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if(sessionOptional.isEmpty()){
            return SessionStatus.ENDED;

        }
        Session session = sessionOptional.get();
        if(!session.getSessionStatus().equals(SessionStatus.ACTIVE)){
            return SessionStatus.ENDED;
        }

        Jws<Claims> claimsJws = Jwts.parser().build().parseSignedClaims(token);
        String email = (String)claimsJws.getPayload().get("email");
        List<Role> roles = (List<Role>) claimsJws.getPayload().get("roles");
        Date createdAt = (Date) claimsJws.getPayload().get("createdAt");

        if (createdAt.before(new Date())) {
            return SessionStatus.ENDED;
        }

        //Here we can write logic to match the IP.
        return SessionStatus.ACTIVE;
    }
}

