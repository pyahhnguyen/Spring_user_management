package com.example.jwat_g.exception;

/**
 * Exception khi User đã tồn tại (duplicate username/email)
 */
public class UserAlreadyExistsException extends RuntimeException {
    
    public UserAlreadyExistsException(String message) {
        super(message);
    }
    
    public UserAlreadyExistsException(String field, String value) {
        super(String.format("User already exists with %s: %s", field, value));
    }
}

