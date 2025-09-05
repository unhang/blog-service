package com.hang.blogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private UUID id;
    private String username;
    private String email;
    private boolean isActive;
    private boolean isVerified;
    private boolean isTest;
    private LocalDateTime verifiedTime;
    private LocalDateTime loggedInTime;
    private RoleDto role;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoleDto {
        private Integer id;
        private String name;
        private String description;
    }
}