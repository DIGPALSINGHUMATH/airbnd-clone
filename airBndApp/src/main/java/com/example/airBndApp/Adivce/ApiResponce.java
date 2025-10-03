package com.example.airBndApp.Adivce;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponce<T> {
    private T data;

    private ApiError apiError;
//    @JsonFormat(pattern = "hh:mm:ss dd/MM/yyyy")
    private LocalDateTime timeStamp;


    public ApiResponce() {
        this.timeStamp =  LocalDateTime.now();
    }

    public ApiResponce(T data) {
        this();
        this.data = data;
    }

    public ApiResponce(ApiError apiError) {
        this();
        this.apiError = apiError;
    }
}
