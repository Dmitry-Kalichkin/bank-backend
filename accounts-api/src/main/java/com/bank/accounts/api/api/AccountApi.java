package com.bank.accounts.api.api;

import com.bank.accounts.api.configuration.AccountClientConfiguration;
import com.bank.accounts.api.dto.AccountDto;
import com.bank.accounts.api.dto.CreateAccountDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "account-service", url = "${accounts-service.url}",
        configuration = AccountClientConfiguration.class)
public interface AccountApi {
    @PostMapping
    AccountDto save(@RequestBody @Valid CreateAccountDto createAccountDto);

    @RequestMapping(method = RequestMethod.PATCH, path= "/close/{id}")
    void close(@PathVariable Long id);
}
