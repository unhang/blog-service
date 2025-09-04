package com.hang.blogservice.enity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "account")
public class Account extends BaseEnity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "is_test")
    private boolean isTest;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name ="verified_time")
    private LocalDateTime verifiedTime;

    @Column(name = "logged_in_time")
    private LocalDateTime loggedInTime;

    @Column(name ="role_id")
    private Integer roleId;
}
