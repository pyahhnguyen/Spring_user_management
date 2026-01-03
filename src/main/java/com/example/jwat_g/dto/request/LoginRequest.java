package com.example.jwat_g.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
