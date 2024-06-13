package com.example.userservicefinal.controllers;

import com.example.userservicefinal.dtos.SetUserRolesRequestDTO;
import com.example.userservicefinal.dtos.UserDTO;
import com.example.userservicefinal.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable("id") Long userId) {
        UserDTO userDto = userService.getUserDetails(userId);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDTO> setUserDetails(@RequestParam  @PathVariable("id") Long userId, @RequestBody SetUserRolesRequestDTO request){
        UserDTO userDTO = userService.setUserRoles(userId, request.getRoleIds());

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
