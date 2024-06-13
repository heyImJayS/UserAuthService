package com.example.userservicefinal.security;

import com.example.userservicefinal.models.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@JsonDeserialize(as = CustomSpringGrantedAuthorities.class)
@NoArgsConstructor
public class CustomSpringGrantedAuthorities implements GrantedAuthority {

    private Role role;

    public CustomSpringGrantedAuthorities(Role role){
        this.role= role;
    }
    @Override
    @JsonIgnore
    public String getAuthority() {
        return role.getRole();
    }
}
