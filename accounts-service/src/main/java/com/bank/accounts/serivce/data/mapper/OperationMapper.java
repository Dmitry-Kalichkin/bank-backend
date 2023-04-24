package ru.bank.accounts.data.mapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.bank.accounts.data.dto.operation.CreateOperationDto;
import ru.bank.accounts.data.dto.operation.OperationDto;
import ru.bank.accounts.data.entity.AccountEntity;
import ru.bank.accounts.data.entity.OperationEntity;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OperationMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    private void setup() {
        modelMapper.createTypeMap(OperationEntity.class, OperationDto.class)
                .addMapping(source -> source.getAccount().getId(),
                        OperationDto::setAccountId)
                .addMapping(source -> source.getDestinationAccount().getId(),
                        OperationDto::setDestinationAccountId);
        modelMapper.createTypeMap(CreateOperationDto.class, OperationEntity.class)
                .addMappings(modelMapper -> {
                    modelMapper.skip(OperationEntity::setAccount);
                    modelMapper.skip(OperationEntity::setDestinationAccount);
                });
    }

    public OperationEntity toEntity(CreateOperationDto createOperationDto,
                                    AccountEntity source, AccountEntity dest) {
        if (Objects.isNull(createOperationDto)) {
            return null;
        } else {
            OperationEntity operationEntity = modelMapper
                    .map(createOperationDto, OperationEntity.class);

            //if (Objects.nonNull(source)) {
                operationEntity.setAccount(source);
            //}

            //if (Objects.nonNull(dest)) {
                operationEntity.setDestinationAccount(dest);
            //}
            return operationEntity;
        }
    }

    public OperationDto toDto(OperationEntity operationEntity) {
        return Objects.isNull(operationEntity) ? null :
                modelMapper.map(operationEntity, OperationDto.class);
    }
}
