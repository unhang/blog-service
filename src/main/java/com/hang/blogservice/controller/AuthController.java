package com.hang.blogservice.controller;


import com.hang.blogservice.dto.AccountRequestDto;
import com.hang.blogservice.dto.AccountResponseDto;
import com.hang.blogservice.dto.AuthResponseDto;
import com.hang.blogservice.service.impl.AuthServiceImpl;
import com.hang.blogservice.validator.CreateAccountValidationGroup;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("register")
    public ResponseEntity<AccountResponseDto> registerAccount(
            @Validated({Default.class})
            @RequestBody AccountRequestDto accountRequestDto) {
        return ResponseEntity.ok(authService.registerAccount(accountRequestDto));
    }


    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(
            @Validated({Default.class, CreateAccountValidationGroup.class})
            @RequestBody AccountRequestDto loginBody) {
        return ResponseEntity.ok(authService.login(loginBody));
    }
}
