package com.hang.blogservice.service;

import com.hang.blogservice.dto.AccountRequestDto;
import com.hang.blogservice.dto.AccountResponseDto;
import com.hang.blogservice.dto.AuthResponseDto;

public interface AuthService {
    AccountResponseDto registerAccount(AccountRequestDto accountRequestDto);
    AuthResponseDto login(AccountRequestDto accountRequestDto);
    AuthResponseDto refreshToken(AccountRequestDto accountRequestDto);
    void logout(AccountRequestDto  accountRequestDto);
}
