package com.hang.blogservice.service.impl;

import com.hang.blogservice.dto.AccountRequestDto;
import com.hang.blogservice.dto.AccountResponseDto;
import com.hang.blogservice.entity.Account;
import com.hang.blogservice.exception.EmailExistsException;
import com.hang.blogservice.mapper.AccountMapper;
import com.hang.blogservice.repository.AccountRepository;
import com.hang.blogservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public List<AccountResponseDto> findAll() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountResponseDto> accountResponseDtos = accounts
                .stream()
                .map(AccountMapper::toDto)
                .toList();
        return accountResponseDtos;
    }

    @Override
    public AccountResponseDto create(AccountRequestDto accountRequestDto) {
        if (accountRepository.existsByEmail(accountRequestDto.getEmail())) {
            throw new EmailExistsException("Email already exists");
        }

        Account account = AccountMapper.toEntity(accountRequestDto);
//      default role id = 1
//        account.setRoleId(1);
        account = accountRepository.save(account);
        return AccountMapper.toDto(account);
    }

//    @Override
//    public AccountResponseDto update(Account account){
//
//    }
}
