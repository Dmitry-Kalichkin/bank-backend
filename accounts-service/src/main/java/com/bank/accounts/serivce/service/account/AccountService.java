package ru.bank.accounts.service.account;

import org.springframework.stereotype.Service;
import ru.bank.accounts.data.dto.account.AccountDto;
import ru.bank.accounts.data.dto.account.LightAccountDto;
import ru.bank.accounts.data.dto.account.CreateAccountDto;

import java.util.List;
import java.util.UUID;

@Service
public interface AccountService {
    AccountDto open(CreateAccountDto createAccountDto);
    List<LightAccountDto> getAllByUserId(UUID userId);
    void close(Long id);
    void block(Long id);
}
