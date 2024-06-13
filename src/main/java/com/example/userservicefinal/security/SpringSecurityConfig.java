package com.example.userservicefinal.security;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SpringSecurityConfig {
    /*
    //We know once we add dependency of Spring Security by Default it add the Authentication for all the APIs in project
    //But we dont want that so we disable that
    @Bean     //By assigning @Bean Java will create the Object when application startO
    @Order(1)
    public SecurityFilterChain filteringCriteria(HttpSecurity httpSec) throws Exception{
        httpSec.cors().disable();    //deprecated
        httpSec.csrf().disable();    //deprecated
        //SecurityFilterChain  -> Object that handles what all API endpoints should be authenticated VS what all shouldn't authenticated.

        httpSec.authorizeHttpRequests(authorize ->authorize.anyRequest().permitAll());

        //httpSec.authorizeHttpRequests(authorize -> authorize.requestMatchers("/auth/logout").denyAll());   //It will not allow any user to access this endpoint
        //httpSec.authorizeHttpRequests(authorize -> authorize.requestMatchers("/auth/*").authenticated());  //Only user who is loggedIn is allowed to access this endpoint

        return httpSec.build();
    }
    */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

