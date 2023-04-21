package com.bank.accounts.serivce.controller;

import com.bank.accounts.api.api.AccountApi;
import com.bank.accounts.api.dto.AccountDto;
import com.bank.accounts.api.dto.CreateAccountDto;
import com.bank.accounts.api.dto.LightAccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class AccountController implements AccountApi {
    @Override
    public AccountDto save(CreateAccountDto createAccountDto) {
        return null;
    }

    @Override
    public void close(Long id) {

    }

    @GetMapping("/test")
    public String test() {
        log.info("Received request");
        return "Success";
    }
}
