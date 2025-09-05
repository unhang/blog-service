package com.hang.blogservice.service;

import com.hang.blogservice.dto.AccountRequestDto;
import com.hang.blogservice.dto.AccountResponseDto;

import java.util.List;

public interface AccountService {
    List<AccountResponseDto> findAll();
    AccountResponseDto create(AccountRequestDto account);
//    AccountResponseDto update(Account account);
}
