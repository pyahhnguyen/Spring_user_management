package com.example.jwat_g.service;

import com.example.jwat_g.dto.request.LoginRequest;
import com.example.jwat_g.dto.request.RefreshTokenRequest;
import com.example.jwat_g.dto.request.RegisterRequest;
import com.example.jwat_g.dto.response.JwtResponse;
import com.example.jwat_g.exception.TokenRefreshException;
import com.example.jwat_g.exception.UserAlreadyExistsException;
import com.example.jwat_g.mapper.AccountMapper;
import com.example.jwat_g.model.Account;
import com.example.jwat_g.model.RefreshToken;
import com.example.jwat_g.security.jwt.JwtUtils;
import com.example.jwat_g.security.userdetail.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String accessToken = jwtUtils.generateJwtToken(authentication);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .role(userDetails.getRole())
                .build();
    }

    @Transactional
    public void register(RegisterRequest request) {
        if (accountMapper.isEmailExist(request.getEmail())) {
            throw new UserAlreadyExistsException("email", request.getEmail());
        }

        Account account = new Account();
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setFullName(request.getFullName());
        account.setRole("USER"); // Default role for registration

        accountMapper.insertAccount(account);
    }

    public JwtResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());

        Account account = accountMapper.getAccountById(refreshToken.getAccountId());
        if (account == null) {
            throw new TokenRefreshException("Account not found for refresh token");
        }

        String newAccessToken = jwtUtils.generateTokenFromUsername(account.getEmail());

        return JwtResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken.getToken())
                .id(account.getId())
                .username(account.getEmail())
                .email(account.getEmail())
                .role(account.getRole())
                .build();
    }

    public void logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            refreshTokenService.deleteByAccountId(userDetails.getId());
        }
        SecurityContextHolder.clearContext();
    }
}

