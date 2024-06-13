package com.example.userservicefinal.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ApiResponseObject implements Serializable {
    private String message;
    private LocalDateTime timeStamp;
    public ApiResponseObject(){
        this.timeStamp= LocalDateTime.now();
    }

}
