package com.example.userservicefinal.security;

import com.example.userservicefinal.models.Role;
import com.example.userservicefinal.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@JsonDeserialize(as = CustomSpringUserDetail.class)
public class CustomSpringUserDetail implements UserDetails, Serializable {
    private User user;
    //Jackson Library need default constructor to deserialize
    public CustomSpringUserDetail( ){}
    public CustomSpringUserDetail( User user){
        this.user= user;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<CustomSpringGrantedAuthorities> customSpringGrantedAuthoritiesList = new ArrayList<>();
        for(Role role: user.getRoles()){
            customSpringGrantedAuthoritiesList.add(new CustomSpringGrantedAuthorities(role));
        }
         return customSpringGrantedAuthoritiesList;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
    //Took from link
    //https://docs.spring.io/spring-authorization-server/reference/guides/how-to-custom-claims-authorities.html
//    @Bean
//    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
//        return (context) -> {
//            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
//                context.getClaims().claims((claims) -> {
//                    claims.put("claim-1", "value-1");
//                    claims.put("claim-2", "value-2");
//                });
//            }
//        };
//    }
}
