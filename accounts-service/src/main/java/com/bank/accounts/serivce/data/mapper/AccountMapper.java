package ru.bank.accounts.data.mapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.bank.accounts.data.dto.account.AccountDto;
import ru.bank.accounts.data.dto.account.LightAccountDto;
import ru.bank.accounts.data.dto.account.CreateAccountDto;
import ru.bank.accounts.data.entity.AccountEntity;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    private void setup() {
        modelMapper
                .createTypeMap(CreateAccountDto.class, AccountEntity.class)
                .addMapping(source -> 0, AccountEntity::setAmount);
        modelMapper
                .createTypeMap(AccountEntity.class, LightAccountDto.class)
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(AccountEntity.class, AccountDto.class);
    }

    private Converter<AccountEntity, LightAccountDto> toDtoConverter() {
        return mappingContext -> {
            AccountEntity accountEntity = mappingContext.getSource();
            LightAccountDto accountDto = mappingContext.getDestination();

            accountDto.setIsClosed(Objects.nonNull(accountEntity.getClosingDate()));
            accountDto.setIsBlocked(Objects.nonNull(accountEntity.getBlockingDate()));

            return accountDto;
        };
    }

    public AccountEntity toEntity(CreateAccountDto createAccountDto) {
        return Objects.isNull(createAccountDto) ? null :
                modelMapper.map(createAccountDto, AccountEntity.class);
    }

    public LightAccountDto toLightDto(AccountEntity accountEntity) {
        return Objects.isNull(accountEntity) ? null :
                modelMapper.map(accountEntity, LightAccountDto.class);
    }

    public AccountDto toDto(AccountEntity accountEntity) {
        return Objects.isNull(accountEntity) ? null :
                modelMapper.map(accountEntity, AccountDto.class);
    }
}
