package com.hang.blogservice.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtSecurityConfig {
    @Value("jwt.secret")
    private String secret;
    private long accessTokenExpiration = 1800000; // 30 minutes in milliseconds
    private long refreshTokenExpiration = 2592000000L; // 30 days in milliseconds
    private String tokenPrefix = "Bearer ";
    private String headerString = "Authorization";
}
