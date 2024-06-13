package com.example.userservicefinal.services;

import com.example.userservicefinal.dtos.SetUserRolesRequestDTO;
import com.example.userservicefinal.dtos.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public UserDTO getUserDetails(Long userId);
    public UserDTO setUserRoles(Long userId, List<Long> roleIds);
}
