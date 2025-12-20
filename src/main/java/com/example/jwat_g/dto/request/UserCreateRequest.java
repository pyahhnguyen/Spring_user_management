package com.example.jwat_g.dto.request;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
}