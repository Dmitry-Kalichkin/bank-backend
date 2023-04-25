package com.bank.loans.service.payment;

import com.bank.accounts.api.api.AccountApi;
import com.bank.commons.dto.PageDto;
import com.bank.loans.data.dto.operation.LoanOperationDto;
import com.bank.loans.data.dto.operation.OperationResponseDto;
import com.bank.loans.data.dto.payment.PaymentDto;
import com.bank.loans.data.entity.LoanEntity;
import com.bank.loans.data.entity.PaymentEntity;
import com.bank.loans.data.enums.KafkaTopic;
import com.bank.loans.data.exception.LoanNotExistsException;
import com.bank.loans.data.exception.PaymentNotFoundException;
import com.bank.loans.data.mapper.PaymentMapper;
import com.bank.loans.repository.LoanRepository;
import com.bank.loans.repository.PaymentRepository;
import com.bank.loans.service.rating.CreditRatingService;
import com.bank.loans.util.PageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.RandomAccess;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;
    private final LoanRepository loanRepository;
    private final PaymentMapper paymentMapper;
    private final CreditRatingService creditRatingService;
    private final KafkaTemplate<String, LoanOperationDto> kafkaTemplate;
    private final AccountApi accountApi;

    @Override
    @Transactional(readOnly = true)
    public PaymentDto getById(Long id) {
        PaymentEntity paymentEntity = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new PaymentNotFoundException("payment with id " + id
                                + " not found"));

        return paymentMapper.toDto(paymentEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public PageDto<PaymentDto> getPage(Long loanId, Pageable page) {
        loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotExistsException("loan with id " + loanId
                        + " not found"
                ));

        Page<PaymentEntity> paymentEntities = paymentRepository
                .getAllByLoanId(loanId, page);

        return createPageDto(paymentEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public PageDto<PaymentDto> getAllOverduedByLoanId(Long loanId, Pageable page) {
        Page<PaymentEntity> paymentEntities = paymentRepository
                .getAllByLoanIdAndIsOverdue(loanId, true, page);

        return createPageDto(paymentEntities);
    }

    private PageDto<PaymentDto> createPageDto(Page<PaymentEntity> paymentEntities) {
        PageDto<PaymentDto> paymentDtos = new PageDto<>();
        paymentDtos.setPage(paymentEntities.getNumber());
        paymentDtos.setTotalPages(paymentEntities.getTotalPages());
        paymentDtos.setSize(paymentEntities.getSize());
        paymentDtos.setObjects(paymentEntities
                .stream().map(paymentMapper::toDto)
                .collect(Collectors.toList()));

        return paymentDtos;
    }

    @Transactional
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    public void payForLoans() {
        Date currentDate = new Date();

        Pageable pageRequest = PageUtils.create(0, 1000, true);

        List<PaymentEntity> paymentEntities = paymentRepository
                .getByDateAndIsOverdue(currentDate, null, pageRequest);

        int i = 1;

        while (paymentEntities.size() > 0) {
            sendMessages(paymentEntities);

            pageRequest = PageUtils.create(i, 1000, true);

            paymentEntities = paymentRepository
                    .getByDateAndIsOverdue(currentDate, null, pageRequest);
            i++;
        }
    }

    private void sendMessages(List<PaymentEntity> paymentEntities) {
        /*
           Iterate type depends on type of list.
           Total iterations for payments could be very large,
           so I decided to do this.
         */

        if (paymentEntities instanceof RandomAccess) {
            for (short i = 0; i < paymentEntities.size(); i++) {
                long accountId = paymentEntities.get(i).getLoan().getAccountId();
                LoanOperationDto loanOperationDto = new LoanOperationDto();

                loanOperationDto.setPaymentId(paymentEntities.get(i).getId());
                loanOperationDto.setAccountId(accountId);
                loanOperationDto.setAmount(paymentEntities.get(i).getAmount());
                loanOperationDto.setDestinationAccountId(null);
                kafkaTemplate.send(
                        KafkaTopic.LOANS_PAYMENTS_OPERATIONS_TOPIC_NAME.getValue(),
                        loanOperationDto
                );
            }
        } else {
            for (PaymentEntity paymentEntity : paymentEntities) {
                long accountId = paymentEntity.getLoan().getAccountId();

                LoanOperationDto loanOperationDto = new LoanOperationDto();

                loanOperationDto.setPaymentId(paymentEntity.getId());
                loanOperationDto.setAccountId(accountId);
                loanOperationDto.setAmount(paymentEntity.getAmount());
                loanOperationDto.setDestinationAccountId(null);

                kafkaTemplate.send(
                        KafkaTopic.LOANS_PAYMENTS_OPERATIONS_TOPIC_NAME.getValue(),
                        loanOperationDto
                );
            }
        }
    }

    @Override
    @Transactional
    @KafkaListener(id = "operation listener",
            topics = "loans-payments-responses",
            groupId = "operations-handlers-1")
    public void receiveReplies(@Payload OperationResponseDto operationResponseDto) {

        Optional<PaymentEntity> paymentEntityOptional = paymentRepository
                .findById(operationResponseDto.getId());

        if (paymentEntityOptional.isPresent()) {
            PaymentEntity paymentEntity = paymentEntityOptional.get();

            if (operationResponseDto.isSuccessfully()) {
                LoanEntity loanEntity = paymentEntity.getLoan();
                loanEntity.setAmountRemain(loanEntity.getAmountRemain()
                        - paymentEntity.getAmount());

                if (loanEntity.getAmountRemain() == 0) {
                    loanEntity.setIsClosed(true);
                    try {
                        accountApi.close(loanEntity.getAccountId());
                    } catch (Exception exception) {
                        log.warn("cant close account with id {}",
                                loanEntity.getAccountId());
                        System.out.println(exception);
                    }
                }
            }

            paymentEntity.setIsOverdue(!operationResponseDto.isSuccessfully());
        }
    }
}
