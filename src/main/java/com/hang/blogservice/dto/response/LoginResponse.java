package com.hang.blogservice.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String password; // TODO: remove this after testing
}
