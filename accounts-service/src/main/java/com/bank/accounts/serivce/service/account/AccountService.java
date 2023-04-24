package com.bank.accounts.serivce.service.account;

import com.bank.accounts.api.dto.AccountDto;
import com.bank.accounts.api.dto.CreateAccountDto;
import com.bank.accounts.api.dto.LightAccountDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AccountService {
    AccountDto open(CreateAccountDto createAccountDto);
    List<LightAccountDto> getAllByUserId(UUID userId);
    void close(Long id);
    void block(Long id);
}
