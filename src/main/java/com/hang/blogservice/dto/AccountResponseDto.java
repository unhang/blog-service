package com.hang.blogservice.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountResponseDto {
    private String id;
    private String username;
    private boolean isActive;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime lastLoginTime;
    private Integer roleId;
    private String accessToken;
}
