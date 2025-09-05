package com.hang.blogservice.controller;


import com.hang.blogservice.dto.*;
import com.hang.blogservice.service.AuthService;
import com.hang.blogservice.service.AuthenService;
import com.hang.blogservice.service.impl.AuthServiceImpl;
import com.hang.blogservice.validator.CreateAccountValidationGroup;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenService authenService;
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse<AccountResponseDto>> registerAccount(
            @Validated({Default.class})
            @RequestBody AccountRequestDto accountRequestDto) {

        return ResponseEntity.ok(ApiResponse.success("Create success", authService.registerAccount(accountRequestDto)));
    }

//
//    @PostMapping("login")
//    public ResponseEntity<AuthResponseDto> login(
//            @Validated({Default.class, CreateAccountValidationGroup.class})
//            @RequestBody AccountRequestDto loginBody) {
//        return ResponseEntity.ok(authService.login(loginBody));
//    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request,
                                                            HttpServletRequest httpRequest) {
        try {
            LoginResponse response = authenService.login(request);
            log.info("Login successful for user: {} from IP: {}",
                    request.getIdentifier(), httpRequest);
            return ResponseEntity.ok(ApiResponse.success("Login successful", response));
        } catch (Exception e) {
            log.warn("Login failed for user: {} from IP: {} - {}",
                    request.getIdentifier(), httpRequest, e.getMessage());
            return ResponseEntity.status(401)
                    .body(ApiResponse.error("Login failed: " + e.getMessage()));
        }
    }

}
