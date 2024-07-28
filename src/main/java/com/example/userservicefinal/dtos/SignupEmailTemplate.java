package com.example.userservicefinal.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupEmailTemplate {
    private String to;
    private String from;
    private String subject;
    private String body;
}
