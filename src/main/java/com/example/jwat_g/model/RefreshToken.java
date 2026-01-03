package com.example.jwat_g.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RefreshToken {
    private Long id;
    private Long accountId;
    private String token;
    private LocalDateTime expiryDate;
    private LocalDateTime createdAt;

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }
}
