package com.hang.blogservice.controller;

import com.hang.blogservice.dto.AccountRequestDto;
import com.hang.blogservice.dto.AccountResponseDto;
import com.hang.blogservice.service.impl.AccountServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("api/account")
public class AccountController {

    private final AccountServiceImpl accountService;

    @GetMapping("")
    public ResponseEntity<List<AccountResponseDto>> findAll() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> create(
            @Validated({Default.class})
            @RequestBody AccountRequestDto account) {
        return ResponseEntity.ok(accountService.create(account));
    }

}
