package com.example.jwat_g.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Account {
    private Long id;
    private String email;
    private String password;
    private String fullName;
    private String role;
    private LocalDateTime createdAt;
}
