package com.example.eduboost_backend.dto.error;

import lombok.Data;

@Data
public class ErrorResponse {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private String details;
    
    public ErrorResponse(int status, String error, String message, String path, String details) {
        this.timestamp = new java.util.Date().toString();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.details = details;
    }
}
