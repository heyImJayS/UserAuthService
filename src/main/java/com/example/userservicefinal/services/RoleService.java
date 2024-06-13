package com.example.userservicefinal.services;

import com.example.userservicefinal.models.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    public Role createRole(String name);
}
