package com.hang.blogservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RefreshToken> refreshTokens;


    @PrePersist
    protected void onCreate() {
        if (role == null) {
            // Default to normal user role
            role = new Role();
            role.setId(2);
        }
    }

    public boolean isAdmin() {
        return role != null && role.getId().equals(1);
    }
}
