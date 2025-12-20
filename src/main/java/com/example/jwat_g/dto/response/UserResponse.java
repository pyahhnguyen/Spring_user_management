package com.example.jwat_g.dto.response;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String status;
    private LocalDateTime createdAt;

}