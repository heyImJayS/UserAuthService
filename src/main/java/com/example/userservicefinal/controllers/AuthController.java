package com.example.userservicefinal.controllers;

import com.example.userservicefinal.dtos.*;
import com.example.userservicefinal.messaging.KafkaProducerConfig;
import com.example.userservicefinal.models.SessionStatus;
import com.example.userservicefinal.response.ApiEntity;
import com.example.userservicefinal.response.ApiResponseObject;
import com.example.userservicefinal.services.AuthService;
import com.example.userservicefinal.util.UserServiceConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private KafkaProducerConfig producer;

    @PostMapping("/signin")
    public ResponseEntity<ApiResponseObject> signin(@RequestBody LoginRequestDTO req){
        return  authService.login(req.getUsername(), req.getPassword());
    }
    @PostMapping(value = "/signup", consumes = "application/json")
    //@PreAuthorize()
    public ResponseEntity<ApiResponseObject> signup(@RequestBody SignUpRequestDTO req){
        UserDTO userDTO = authService.signup(req.getEmail(), req.getPassword());

        //String topicName = topicConfig.topic1().name();
        //Here I am sending the details to Queue, of user who just signedUp
        //This is required if some other service want this information when user Sign's Up
        try{
            producer.sendMessage(UserServiceConstants.EMAIL_SIGNUP_TOPIC, objectMapper.writeValueAsString(userDTO));

            //Lets pt email in the queue
            SignupEmailTemplate signupEmailTemplate= new SignupEmailTemplate();
            signupEmailTemplate.setTo(userDTO.getEmail());
            signupEmailTemplate.setFrom("swain.dhananjaya.15@gmail.com");
            signupEmailTemplate.setSubject("Welcome to ECommerce Portal");
            signupEmailTemplate.setBody("Thank you for signing In ");
            producer.sendMessage(UserServiceConstants.EMAIL_SIGNUP_TOPIC, objectMapper.writeValueAsString(signupEmailTemplate) );
        }catch(Exception e){
            System.out.println("Some error happen while adding to Queue");
        }

        String message = "User created";
        return new ResponseEntity<ApiResponseObject>(new ApiEntity<UserDTO>(message, userDTO), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequestDTO req){
        HttpStatus httpStatus = HttpStatus.OK;
        String response= authService.logout(req.getToken(),req.getUserId());
        return new ResponseEntity<>(response,httpStatus);
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(ValidateTokenRequestDTO req){
        SessionStatus sessionStatus = authService.validate(req.getToken(), req.getUserId());
        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
    }
}
