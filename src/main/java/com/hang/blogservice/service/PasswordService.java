package com.hang.blogservice.service;

import jakarta.security.auth.message.callback.PasswordValidationCallback;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


// Helper class: BEncrypt and Decrypt
@Service
@AllArgsConstructor
public class PasswordService {

//    BCryptPasswordEncoder
    private final PasswordEncoder passwordEncoder;
//    private final PasswordValidationCallback passwordValidationCallback;

    /**
     * return hashed password by BEncrypt
     */
    public String hashPassword(String plainPassword){
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        String hashedPassword = passwordEncoder.encode(plainPassword);

        return hashedPassword;
    }

    /**
     * Verify a plain text password against a hashed password
     */
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }

        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
}

