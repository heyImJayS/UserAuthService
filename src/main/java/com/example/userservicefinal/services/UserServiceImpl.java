package com.example.userservicefinal.services;


import com.example.userservicefinal.dtos.SetUserRolesRequestDTO;
import com.example.userservicefinal.dtos.UserDTO;
import com.example.userservicefinal.models.Role;
import com.example.userservicefinal.models.User;
import com.example.userservicefinal.repositories.RoleRepository;
import com.example.userservicefinal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getUserDetails(Long userId) {
        //System.out.println("I got the request");
        //return new UserDto();
       Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return null;
        }
        return UserDTO.from(userOptional.get());
    }
    @Override
    public UserDTO setUserRoles(Long userId, List<Long> roleIds) {
        Optional<User> userOptional = userRepository.findById(userId);
        List<Role> userRoles = roleRepository.findAllByIdIn(roleIds);
        if (userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();
        Set<Role> userRoleSet = user.getRoles();
        userRoleSet.addAll(userRoles);
        user.setRoles(userRoleSet);
        User savedUser = userRepository.save(user);
        return UserDTO.from(savedUser);
    }
}
