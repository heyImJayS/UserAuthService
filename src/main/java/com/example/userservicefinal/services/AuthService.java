package com.example.userservicefinal.services;

import com.example.userservicefinal.dtos.UserDTO;
import com.example.userservicefinal.models.SessionStatus;
import com.example.userservicefinal.response.ApiResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public ResponseEntity<ApiResponseObject> login(String username, String password);
    public UserDTO signup(String username, String password);
    public String logout(String token, Long userId);
    public SessionStatus validate(String token, Long userId);
}
