package com.hang.blogservice.util;

import com.hang.blogservice.configuration.JwtSecurityConfig;
import com.hang.blogservice.entity.Account;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private final JwtSecurityConfig jwtConfig;

    private SecretKey getSigningKey() {
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        SecretKey signingKey = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
        log.info("===========================================");
        log.info("Create JWT Token {}",signingKey);
        log.info("Create JWT Token {}",signingKey.getEncoded());
        log.info("===========================================");
        return signingKey;
    }

    public String generateAccessToken(Account account) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", account.getId().toString());
        claims.put("username", account.getUsername());
        claims.put("email", account.getEmail());
        claims.put("roleId", account.getRole().getId());
        claims.put("roleName", account.getRole().getName());
        claims.put("isActive", account.isActive());
        log.info("===========================================");
        log.info(claims.toString());
        log.info("===========================================");

        return createToken(claims, account.getUsername(), jwtConfig.getAccessTokenExpiration());
    }

    public String generateRefreshToken(Account account) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", account.getId().toString());
        claims.put("type", "refresh");

        return createToken(claims, account.getUsername(), jwtConfig.getRefreshTokenExpiration());
    }

    private String createToken( Map<String, Object> claims, String subject, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public UUID getUserIdFromToken(String token) {
        String userIdStr = (String) getClaimsFromToken(token).get("userId");
        return UUID.fromString(userIdStr);
    }

    public Integer getRoleIdFromToken(String token) {
        return (Integer) getClaimsFromToken(token).get("roleId");
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public boolean validateToken(String token, Account account) {
        try {
            String username = getUsernameFromToken(token);
            return (username.equals(account.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            log.error("Token validation error: ", e);
            return false;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("JWT token is malformed: {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("JWT signature does not match: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid: {}", e.getMessage());
        }
        return false;
    }
}