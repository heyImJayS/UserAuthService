package com.example.userservicefinal.controllers;

import com.example.userservicefinal.dtos.*;
import com.example.userservicefinal.models.SessionStatus;
import com.example.userservicefinal.response.ApiEntity;
import com.example.userservicefinal.response.ApiResponseObject;
import com.example.userservicefinal.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<ApiResponseObject> signin(@RequestBody LoginRequestDTO req){
        return  authService.login(req.getUsername(), req.getPassword());
    }
    @PostMapping(value = "/signup", consumes = "application/json")
    public ResponseEntity<ApiResponseObject> signup(@RequestBody SignUpRequestDTO req){
        UserDTO userDTO = authService.signup(req.getEmail(), req.getPassword());
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
