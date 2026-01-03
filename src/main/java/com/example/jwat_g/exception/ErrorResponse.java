package com.example.jwat_g.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private boolean success = false;
    private LocalDateTime timestamp;
    private String message;
    private int statusCode;
    private String error;
    private String path;

    public ErrorResponse(String message, int statusCode) {
        this.success = false;
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String message, int statusCode, String errorCode) {
        this.success = false;
        this.message = message;
        this.statusCode = statusCode;
        this.error = errorCode;
        this.timestamp = LocalDateTime.now();
    }
}