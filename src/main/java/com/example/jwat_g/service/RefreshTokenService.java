package com.example.jwat_g.service;

import com.example.jwat_g.exception.TokenRefreshException;
import com.example.jwat_g.mapper.RefreshTokenMapper;
import com.example.jwat_g.model.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${app.jwt.refresh-expiration-ms}")
    private long refreshTokenExpirationMs;

    private final RefreshTokenMapper refreshTokenMapper;

    @Transactional
    public RefreshToken createRefreshToken(Long accountId) {
        refreshTokenMapper.deleteByAccountId(accountId);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setAccountId(accountId);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(refreshTokenExpirationMs / 1000));

        refreshTokenMapper.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenMapper.findByToken(token);

        if (refreshToken == null) {
            throw new TokenRefreshException("Refresh token not found");
        }

        if (refreshToken.isExpired()) {
            refreshTokenMapper.deleteByToken(token);
            throw new TokenRefreshException("Refresh token has expired. Please login again");
        }

        return refreshToken;
    }

    @Transactional
    public void deleteByAccountId(Long accountId) {
        refreshTokenMapper.deleteByAccountId(accountId);
    }

    @Transactional
    public void deleteExpiredTokens() {
        refreshTokenMapper.deleteExpiredTokens();
    }
}
