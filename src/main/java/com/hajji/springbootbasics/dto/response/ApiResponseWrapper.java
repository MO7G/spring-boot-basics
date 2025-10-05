package com.hajji.springbootbasics.dto.response;

import java.time.LocalDateTime;

public class ApiResponseWrapper<T> {
    private int status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponseWrapper(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponseWrapper(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
        this.timestamp = LocalDateTime.now();
    }


    // Getters
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

