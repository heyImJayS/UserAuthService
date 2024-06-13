package com.example.userservicefinal.services;

import com.example.userservicefinal.models.Role;
import com.example.userservicefinal.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role createRole(String name) {
        Role role =new Role();
        role.setRole(name);

        return roleRepository.save(role);
    }
}
