package com.bank.loans.service.loan;

import com.bank.accounts.api.api.AccountApi;
import com.bank.accounts.api.dto.CreateAccountDto;
import com.bank.loans.data.dto.loan.CreateLoanDto;
import com.bank.loans.data.dto.loan.LightLoanDto;
import com.bank.loans.data.dto.loan.LoanDto;
import com.bank.loans.data.dto.operation.LoanOperationDto;
import com.bank.loans.data.entity.LoanEntity;
import com.bank.loans.data.entity.RateEntity;
import com.bank.loans.data.enums.KafkaTopic;
import com.bank.loans.data.exception.LoanNotExistsException;
import com.bank.loans.data.exception.RateNotExistsException;
import com.bank.loans.data.mapper.LoanMapper;
import com.bank.loans.repository.LoanRepository;
import com.bank.loans.repository.RateRepository;
import com.bank.loans.service.rating.CreditRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final AccountApi accountApi;
    private final LoanRepository loanRepository;
    private final RateRepository rateRepository;
    private final LoanMapper loanMapper;
    private final CreditRatingService creditRatingService;

    private final KafkaTemplate<String, LoanOperationDto> kafkaTemplate;

    @Override
    @Transactional
    public LoanDto open(CreateLoanDto createLoanDto) {
        RateEntity rateEntity = rateRepository.findById(createLoanDto.getRateId())
                .orElseThrow(() -> new RateNotExistsException(
                        "rate with id " + createLoanDto.getRateId() + " not found"));

        CreateAccountDto createAccountDto = new CreateAccountDto();
        createAccountDto.setUserId(createLoanDto.getUserId());
        createAccountDto.setType("Кредитный");

        Long accountId = accountApi.save(createAccountDto).getId();

        LoanEntity loanEntity = loanMapper.toEntity(createLoanDto, rateEntity,
                accountId);
        loanEntity = loanRepository.save(loanEntity);
        creditRatingService.increaseRating(loanEntity.getUserId(), (short) 100);


        LoanOperationDto loanOperationDto = new LoanOperationDto();

        loanOperationDto.setAccountId(null);
        loanOperationDto.setDestinationAccountId(loanEntity.getAccountId());
        loanOperationDto.setAmount(createLoanDto.getAmount());

        kafkaTemplate.send(
                KafkaTopic.OPERATIONS_TOPIC_NAME.getValue(),
                loanOperationDto
        );
        return loanMapper.toDto(loanEntity);
    }

    @Override
    @Transactional
    public void close(Long id) {
        LoanEntity loanEntity = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotExistsException("loan with id " + id
                        + " not found"
                ));

        loanEntity.setIsClosed(true);
        accountApi.close(loanEntity.getAccountId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LightLoanDto> getAllByUser(UUID userId) {
        List<LoanEntity> loanEntities = loanRepository.findAllByUserId(userId);

        return loanEntities.stream().map(loanMapper::toLightDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LoanDto getById(Long id) {
        LoanEntity loanEntity = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotExistsException("loan with id " + id
                        + " not found"
                ));

        return loanMapper.toDto(loanEntity);
    }
}
