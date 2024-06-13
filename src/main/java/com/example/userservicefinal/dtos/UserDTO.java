package com.example.userservicefinal.dtos;

import com.example.userservicefinal.models.Role;
import com.example.userservicefinal.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private String email;
    private Set<Role> roles = new HashSet<>();
    public static UserDTO from(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setEmail(user.getEmail());
//      userDto.setRoles(user.getRoles());
        return userDto;
    }
}
