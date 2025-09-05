package com.hang.blogservice.mapper;

import com.hang.blogservice.dto.AccountRequestDto;
import com.hang.blogservice.dto.AccountResponseDto;
import com.hang.blogservice.entity.Account;

import java.time.LocalDateTime;

public class AccountMapper {
    public static AccountResponseDto toDto(Account account) {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setId(account.getId().toString());
        accountResponseDto.setUsername(account.getUsername());
        accountResponseDto.setActive(account.isActive());
        accountResponseDto.setUpdatedTime(LocalDateTime.parse(account.getUpdatedTime().toString()));
        accountResponseDto.setCreatedTime(LocalDateTime.parse(account.getCreatedTime().toString()));
        accountResponseDto.setLastLoginTime(account.getLoggedInTime());
        accountResponseDto.setRoleId(account.getRole().getId());
        return  accountResponseDto;
    }

    public static Account toEntity(AccountRequestDto accountRequestDto) {
        Account account = new Account();
        account.setUsername(accountRequestDto.getUsername());
        account.setPassword(accountRequestDto.getPassword());
        account.setEmail(accountRequestDto.getEmail());
        return account;
    }
}
