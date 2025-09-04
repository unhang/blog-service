package com.hang.blogservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("****************************************************");
        log.info("Password Encoder CREATED");
        logger.info("****************************************************");
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for API
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationManager()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // auth endpoints public
//                        .requestMatchers("/api/account/**").permitAll() // account endpoints public
                        .anyRequest().authenticated() // All other endpoints require authentication
                )
                .exceptionHandling(exception -> exception
                                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                        );
        logger.info("****************************************************");
        log.info("FilterChain CREATED");
        logger.info("****************************************************");
        return http.build();
    }
}
