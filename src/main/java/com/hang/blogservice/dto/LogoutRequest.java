package com.hang.blogservice.dto;

import lombok.Data;

@Data
public class LogoutRequest {
    private String refreshToken; // optional, if provided will revoke specific refresh token
}