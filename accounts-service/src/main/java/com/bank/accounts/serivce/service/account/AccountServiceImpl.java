package ru.bank.accounts.service.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bank.accounts.data.dto.account.AccountDto;
import ru.bank.accounts.data.dto.account.LightAccountDto;
import ru.bank.accounts.data.dto.account.CreateAccountDto;
import ru.bank.accounts.data.entity.AccountEntity;
import ru.bank.accounts.data.exception.AccountAmountNotZeroException;
import ru.bank.accounts.data.exception.AccountNotFoundException;
import ru.bank.accounts.data.mapper.AccountMapper;
import ru.bank.accounts.repository.AccountRepository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;


    @Override
    @Transactional
    public AccountDto open(CreateAccountDto createAccountDto) {
        AccountEntity accountEntity = accountMapper.toEntity(createAccountDto);

        accountEntity = accountRepository.save(accountEntity);

        return accountMapper.toDto(accountEntity);
    }

    @Override
    public List<LightAccountDto> getAllByUserId(UUID userId) {
        return accountRepository
                .findAllByUserId(userId)
                .stream().map(accountMapper::toLightDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void close(Long id) {
        AccountEntity accountEntity = accountRepository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException("account with id " + id +
                                " not found"));
        if (accountEntity.getAmount() != 0D) {
            throw new AccountAmountNotZeroException("account amount have to be zero" +
                    " for closing it");
        }
        accountEntity.setClosingDate(new Date(new java.util.Date().getTime()));
    }

    @Override
    @Transactional
    public void block(Long id) {
        AccountEntity accountEntity = accountRepository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException("account with id " + id +
                                " not found"));

        accountEntity.setBlockingDate(new Date(new java.util.Date().getTime()));
    }
}
