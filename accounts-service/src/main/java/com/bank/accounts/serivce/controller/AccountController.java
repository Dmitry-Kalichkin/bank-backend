package com.bank.accounts.serivce.controller;

import com.bank.accounts.api.api.AccountApi;
import com.bank.accounts.api.dto.AccountDto;
import com.bank.accounts.api.dto.CreateAccountDto;
import com.bank.accounts.api.dto.LightAccountDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class AccountController implements AccountApi {
    @Override
    public AccountDto save(CreateAccountDto createAccountDto) {
        return null;
    }

    @Override
    public List<LightAccountDto> getAllByUserId(UUID userId) {
        return null;
    }

    @Override
    public void close(Long id) {

    }

    @GetMapping("/test")
    public String test() {
        return "Success";
    }
}
