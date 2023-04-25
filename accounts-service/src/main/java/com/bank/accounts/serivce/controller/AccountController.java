package com.bank.accounts.serivce.controller;

import com.bank.accounts.api.api.AccountApi;
import com.bank.accounts.api.dto.AccountDto;
import com.bank.accounts.api.dto.CreateAccountDto;
import com.bank.accounts.api.dto.LightAccountDto;
import com.bank.accounts.serivce.service.account.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Endpoints related to accounts.")
public class AccountController implements AccountApi {
    private final AccountService accountService;

    @Override
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto save(@RequestBody @Valid CreateAccountDto createAccountDto) {
        log.info("Saving new account");
        return accountService.open(createAccountDto);
    }

    @Override
    @GetMapping("/user/{userId}")
    public List<LightAccountDto> getByUserId(@PathVariable UUID userId) {
        return accountService.getAllByUserId(userId);
    }

    @PatchMapping("/close/{id}")
    public void close(@PathVariable  Long id) {
        accountService.close(id);
    }

    @PatchMapping("/block/{id}")
    public void block(@PathVariable Long id) {
        accountService.block(id);
    }
}