package com.hang.blogservice.configuration;// ========================================
// 1. JWT CONFIGURATION AND SETUP
// ========================================

import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
public class JwtSecurityConfig {
//
//    @Value("${jwt.secret}")
//    private String jwtSecret;
//
//    @Value("${jwt.expiration:86400}") // 24 hours default
//    private int jwtExpiration;
//
//    @Value("${jwt.refresh-expiration:604800}") // 7 days default
//    private int refreshTokenExpiration;
//
//    // JWT Secret Key Bean
//    @Bean
//    public SecretKey jwtSecretKey() {
//        // Use HMAC-SHA algorithms for symmetric signing
//        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    // JWT Parser Bean
//    @Bean
//    public JwtParser jwtParser(SecretKey secretKey) {
//        return Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build();
//    }
//
//    // Password Encoder
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
//    }
//
//    // Authentication Manager
//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
}