package com.bank.accounts.serivce.service.account;

import com.bank.accounts.api.dto.AccountDto;
import com.bank.accounts.api.dto.CreateAccountDto;
import com.bank.accounts.api.dto.LightAccountDto;
import com.bank.accounts.serivce.data.entity.AccountEntity;
import com.bank.accounts.serivce.data.exception.AccountAmountNotZeroException;
import com.bank.accounts.serivce.data.exception.AccountNotFoundException;
import com.bank.accounts.serivce.data.mapper.AccountMapper;
import com.bank.accounts.serivce.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
