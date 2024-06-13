package com.example.userservicefinal.controllers;

import com.example.userservicefinal.dtos.CreateRoleRequestDTO;
import com.example.userservicefinal.models.Role;
import com.example.userservicefinal.services.RoleService;
import com.example.userservicefinal.services.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> createRole(CreateRoleRequestDTO createRoleRequestDTO){
        Role role = roleService.createRole(createRoleRequestDTO.getName());
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
