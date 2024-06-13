package com.example.userservicefinal.response;

public class ApiEntity<T> extends ApiResponseObject {
    private T data;
    public ApiEntity(T data){
        super();
        this.data= data;
    }
    public ApiEntity(String message, T data){
        super();
        setMessage(message);
        this.data = data;
    }
}
