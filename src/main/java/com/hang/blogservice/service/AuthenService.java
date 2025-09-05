package com.hang.blogservice.service;

import com.hang.blogservice.configuration.JwtSecurityConfig;
import com.hang.blogservice.dto.*;
import com.hang.blogservice.entity.Account;
import com.hang.blogservice.entity.RefreshToken;
import com.hang.blogservice.exception.AuthenticationException;
import com.hang.blogservice.exception.InvalidTokenException;
import com.hang.blogservice.repository.AccountRepository;
import com.hang.blogservice.repository.RefreshTokenRepository;
import com.hang.blogservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthenService {

    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtSecurityConfig jwtConfig;

    public LoginResponse login(LoginRequest request) {
        // Find account by username or email
        log.info("Finding user: {}", request.getIdentifier());
        Account account = accountRepository.findActiveAccountByUsernameOrEmail(request.getIdentifier())
                .orElseThrow(() -> new AuthenticationException("Invalid credentials"));
        log.info("Successful found user: {}", request.getIdentifier());

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }

        // Check if account is active
//        if (!account.isActive()) {
//            throw new AuthenticationException("Account is inactive");
//        }

        // Update logged in time
        account.setLoggedInTime(LocalDateTime.now());
        accountRepository.save(account);

        // Revoke existing refresh tokens for this account
        refreshTokenRepository.revokeAllByAccount(account);

        // Generate new tokens
        String accessToken = jwtUtil.generateAccessToken(account);
        String refreshTokenString = jwtUtil.generateRefreshToken(account);

        // Save refresh token to database
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refreshTokenString);
        refreshToken.setAccount(account);
        refreshToken.setExpiresAt(LocalDateTime.now().plusSeconds(jwtConfig.getRefreshTokenExpiration() / 1000));
        refreshTokenRepository.save(refreshToken);

        // Build response
        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshTokenString);
        response.setExpiresIn(jwtConfig.getAccessTokenExpiration() / 1000); // convert to seconds

        // User info
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo();
        userInfo.setId(account.getId());
        userInfo.setUsername(account.getUsername());
        userInfo.setEmail(account.getEmail());
//        userInfo.setIsActive(account.isActive());
//        userInfo.setIsVerified(account.isVerified());
        userInfo.setActive(account.isActive());
        userInfo.setVerified(account.isVerified());
        userInfo.setLoggedInTime(account.getLoggedInTime());

        // Role info
        LoginResponse.RoleInfo roleInfo = new LoginResponse.RoleInfo();
        roleInfo.setId(account.getRole().getId());
        roleInfo.setName(account.getRole().getName());
        roleInfo.setDescription(account.getRole().getDescription());
        userInfo.setRole(roleInfo);

        response.setUser(userInfo);

        log.info("User {} logged in successfully", account.getUsername());
        return response;
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        String tokenString = request.getRefreshToken();

        // Validate JWT format and expiration
        if (!jwtUtil.validateToken(tokenString)) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        // Find refresh token in database
        RefreshToken refreshToken = refreshTokenRepository.findByTokenAndIsRevokedFalse(tokenString)
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh token"));

        // Check if token is expired
        if (refreshToken.isExpired()) {
            refreshTokenRepository.revokeByToken(tokenString);
            throw new InvalidTokenException("Refresh token has expired");
        }

        // Generate new access token
        String newAccessToken = jwtUtil.generateAccessToken(refreshToken.getAccount());

        RefreshTokenResponse response = new RefreshTokenResponse();
        response.setAccessToken(newAccessToken);
        response.setExpiresIn(jwtConfig.getAccessTokenExpiration() / 1000);

        log.info("Access token refreshed for user {}", refreshToken.getAccount().getUsername());
        return response;
    }

    public void logout(String accessToken, LogoutRequest request) {
        if (accessToken != null && jwtUtil.validateToken(accessToken)) {
            UUID userId = jwtUtil.getUserIdFromToken(accessToken);
            Account account = accountRepository.findById(userId)
                    .orElseThrow(() -> new AuthenticationException("Invalid token"));

            if (request.getRefreshToken() != null) {
                // Revoke specific refresh token
                refreshTokenRepository.revokeByToken(request.getRefreshToken());
                log.info("Specific refresh token revoked for user {}", account.getUsername());
            } else {
                // Revoke all refresh tokens for this user
                refreshTokenRepository.revokeAllByAccount(account);
                log.info("All refresh tokens revoked for user {}", account.getUsername());
            }
        }
    }

    @Scheduled(fixedRate = 3600000) // Run every hour
    public void cleanupExpiredTokens() {
        try {
            refreshTokenRepository.deleteAllExpired(LocalDateTime.now());
            log.debug("Expired refresh tokens cleaned up");
        } catch (Exception e) {
            log.error("Error cleaning up expired tokens", e);
        }
    }

    public Account getCurrentUser(String accessToken) {
        if (accessToken == null || !jwtUtil.validateToken(accessToken)) {
            throw new AuthenticationException("Invalid access token");
        }

        UUID userId = jwtUtil.getUserIdFromToken(accessToken);
        return accountRepository.findById(userId)
                .orElseThrow(() -> new AuthenticationException("User not found"));
    }
}



