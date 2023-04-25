package com.bank.accounts.api.api;

import com.bank.accounts.api.dto.AccountDto;
import com.bank.accounts.api.dto.CreateAccountDto;
import com.bank.accounts.api.dto.LightAccountDto;
import com.bank.commons.dto.PageDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Component
@FeignClient(name = "accounts-service", url = "http://localhost:8080/api/v2/accounts")
public interface AccountApi {
    @PostMapping("/")
    AccountDto save(@RequestBody @Valid CreateAccountDto createAccountDto);

    @GetMapping("/user/{userId}")
    List<LightAccountDto> getByUserId(@PathVariable UUID userId);

    @RequestMapping(method = RequestMethod.PATCH, path= "/close/{id}")
    void close(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.PATCH, path= "/block/{id}")
    void block(@PathVariable Long id);
}