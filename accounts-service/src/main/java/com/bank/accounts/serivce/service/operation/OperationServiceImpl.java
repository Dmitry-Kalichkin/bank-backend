package com.bank.accounts.serivce.service.operation;

import com.bank.accounts.serivce.data.dto.operation.CreateOperationDto;
import com.bank.accounts.serivce.data.dto.operation.LoanOperationDto;
import com.bank.accounts.serivce.data.dto.operation.OperationResponseDto;
import com.bank.accounts.serivce.data.entity.AccountEntity;
import com.bank.accounts.serivce.data.entity.OperationEntity;
import com.bank.accounts.serivce.data.enums.KafkaTopic;
import com.bank.accounts.serivce.data.exception.OperationCantBeCompletedException;
import com.bank.accounts.serivce.data.mapper.OperationMapper;
import com.bank.accounts.serivce.repository.AccountRepository;
import com.bank.accounts.serivce.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {
    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;
    private final OperationMapper operationMapper;
    private final KafkaTemplate<String, OperationResponseDto> template;

    @Override
    @Transactional
    @KafkaListener(id = "operation listener",
            topics = "operations",
            groupId = "operations-handlers-1")
    public void receiveOperation(@Payload CreateOperationDto createOperationDto) {
        OperationEntity operationEntity = createOperationEntity(createOperationDto);

        try {
                if (Objects.nonNull(operationEntity.getAccount())
                    && Objects.isNull(operationEntity.getAccount().getClosingDate())
                        && operationEntity.getAccount().getAmount() >= operationEntity.getAmount()) {
                    operationEntity.getAccount().setAmount(operationEntity.getAccount().getAmount() - operationEntity.getAmount());

                    if (Objects.nonNull(operationEntity.getDestinationAccount())) {
                        operationEntity.getDestinationAccount().setAmount(operationEntity.getDestinationAccount().getAmount() + operationEntity.getAmount());
                    }

                    operationEntity.setIsSuccessfully(true);
                } else if (Objects.isNull(operationEntity.getAccount())
                        && Objects.nonNull(operationEntity.getDestinationAccount())) {
                    operationEntity.getDestinationAccount().setAmount(operationEntity.getDestinationAccount().getAmount() + operationEntity.getAmount());
                    operationEntity.setIsSuccessfully(true);
                } else {
                    operationEntity.setIsSuccessfully(false);
                }
            operationRepository.save(operationEntity);
        } catch (Exception exception) {
            log.warn(exception.getMessage());
            throw new OperationCantBeCompletedException("cant save new operation");
        }
    }

    @Override
    @Transactional
    @KafkaListener(id = "operation with reply listener",
            topics = "loans-payments-operations",
            groupId = "operations-handlers-1")
    public void receiveOperationWithReply(@Payload LoanOperationDto loanOperationDto) {
        OperationEntity operationEntity = createOperationEntity(loanOperationDto);

        try {

            if (Objects.nonNull(operationEntity.getAccount())
                    && Objects.isNull(operationEntity.getAccount().getClosingDate())
                    && operationEntity.getAccount().getAmount() >= operationEntity.getAmount()) {
                operationEntity.getAccount().setAmount(operationEntity.getAccount().getAmount() - operationEntity.getAmount());

                if (Objects.nonNull(operationEntity.getDestinationAccount())) {
                    operationEntity.getDestinationAccount().setAmount(operationEntity.getDestinationAccount().getAmount() + operationEntity.getAmount());
                }

                operationEntity.setIsSuccessfully(true);
            } else if (Objects.isNull(operationEntity.getAccount())
                    && Objects.nonNull(operationEntity.getDestinationAccount())) {
                operationEntity.getDestinationAccount().setAmount(operationEntity.getDestinationAccount().getAmount() + operationEntity.getAmount());
                operationEntity.setIsSuccessfully(true);
            } else {
                operationEntity.setIsSuccessfully(false);
            }
            operationRepository.save(operationEntity);

            OperationResponseDto operationResponseDto = new OperationResponseDto();
            operationResponseDto.setId(loanOperationDto.getPaymentId());
            operationResponseDto.setSuccessfully(operationEntity.getIsSuccessfully());

            template.send("loans-payments-responses", operationResponseDto);
        } catch (Exception exception) {
            log.warn(exception.getMessage());
            throw new OperationCantBeCompletedException("cant save new operation");
        }
    }

    private OperationEntity createOperationEntity(CreateOperationDto createOperationDto) {
        AccountEntity source = null;
        AccountEntity destination = null;
        if (createOperationDto.getAccountId() != null) {
            source = accountRepository
                    .findById(createOperationDto.getAccountId()).orElse(null);
        }

        if (createOperationDto.getDestinationAccountId() != null) {
            destination = accountRepository
                    .findById(createOperationDto.getDestinationAccountId()).orElse(null);
        }

        return operationMapper
                .toEntity(createOperationDto, source, destination);
    }
}