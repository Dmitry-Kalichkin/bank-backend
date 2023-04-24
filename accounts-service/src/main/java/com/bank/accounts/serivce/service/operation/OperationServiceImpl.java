package ru.bank.accounts.service.operation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bank.accounts.config.KafkaConfiguration;
import ru.bank.accounts.data.dto.operation.CreateOperationDto;
import ru.bank.accounts.data.dto.operation.LoanPaymentOperationDto;
import ru.bank.accounts.data.dto.operation.OperationResponseDto;
import ru.bank.accounts.data.entity.AccountEntity;
import ru.bank.accounts.data.entity.OperationEntity;
import ru.bank.accounts.data.enums.KafkaTopic;
import ru.bank.accounts.data.exception.OperationCantBeCompletedException;
import ru.bank.accounts.data.mapper.OperationMapper;
import ru.bank.accounts.repository.AccountRepository;
import ru.bank.accounts.repository.OperationRepository;

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
            if (operationEntity.getAccount().getAmount() >= operationEntity.getAmount()) {
                operationEntity.getAccount().setAmount(operationEntity.getAccount().getAmount() - operationEntity.getAmount());
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
    public void receiveOperationWithReply(@Payload LoanPaymentOperationDto loanPaymentOperationDto) {
        OperationEntity operationEntity = createOperationEntity(loanPaymentOperationDto);
        try {
            if (operationEntity.getAccount().getAmount() >= operationEntity.getAmount()) {
                operationEntity.getAccount().setAmount(operationEntity.getAccount().getAmount() - operationEntity.getAmount());
                operationEntity.setIsSuccessfully(true);
            } else {
                operationEntity.setIsSuccessfully(false);
            }
            operationRepository.save(operationEntity);

            OperationResponseDto operationResponseDto = new OperationResponseDto();
            operationResponseDto.setId(loanPaymentOperationDto.getPaymentId());
            operationResponseDto.setSuccessfully(operationEntity.getIsSuccessfully());

            template.send(KafkaTopic.PAYMENT_RESPONSES_TOPIC_NAME.getValue(), operationResponseDto);
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