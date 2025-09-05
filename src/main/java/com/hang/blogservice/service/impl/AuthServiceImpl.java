package com.hang.blogservice.service.impl;

import com.hang.blogservice.dto.AccountRequestDto;
import com.hang.blogservice.dto.AccountResponseDto;
import com.hang.blogservice.dto.AuthResponseDto;
import com.hang.blogservice.entity.Account;
import com.hang.blogservice.exception.EmailExistsException;
import com.hang.blogservice.exception.ResourceNotFoundException;
import com.hang.blogservice.mapper.AccountMapper;
import com.hang.blogservice.repository.AccountRepository;
import com.hang.blogservice.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

//    private final SecurityConfig securityConfig;
    private final AccountRepository accountRepository;
//    private final PasswordService passwordService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDto login(AccountRequestDto loginRequest) {
        Account account = accountRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + loginRequest.getEmail()));
        System.out.println("****************************************************");
        System.out.println(account.getPassword());
        System.out.println(loginRequest.getPassword());
        System.out.println(passwordEncoder.matches(loginRequest.getPassword(), account.getPassword()));
        System.out.println("****************************************************");
        if (!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            throw new ResourceNotFoundException("Password Mismatch");
        }

        account.setAccessToken("accessToken:" + LocalDateTime.now().toString());
        return new AuthResponseDto(account.getAccessToken());
    }

    @Override
    public AccountResponseDto registerAccount(AccountRequestDto accountRequestDto){
        if (accountRepository.existsByEmail(accountRequestDto.getEmail())) {
            throw new EmailExistsException("Email already exists");
        }

        if (accountRepository.existsByUsername(accountRequestDto.getUsername())) {
            throw new EmailExistsException("Username already exists");
        }

        Account account = new Account();
        account.setUsername(accountRequestDto.getUsername());
        account.setPassword(passwordEncoder.encode(accountRequestDto.getPassword()));
        account.setEmail(accountRequestDto.getEmail());
//        account.(1);
        accountRepository.save(account);

        return AccountMapper.toDto(account);
    }

    @Override
    public AuthResponseDto refreshToken(AccountRequestDto accountRequestDto) {
        // TODO: Implement
        return null;
    }

    @Override
    public void logout(AccountRequestDto accountRequestDto) {
        // TODO: Implement
    }
}
