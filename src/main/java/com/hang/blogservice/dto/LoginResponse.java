package com.hang.blogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private long expiresIn; // in seconds
    private UserInfo user;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private UUID id;
        private String username;
        private String email;
        private boolean isActive;
        private boolean isVerified;
        private LocalDateTime loggedInTime;
        private RoleInfo role;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoleInfo {
        private Integer id;
        private String name;
        private String description;
    }
}