package com.bank.loans.data.mapper;

import com.bank.loans.data.dto.loan.CreateLoanDto;
import com.bank.loans.data.dto.loan.LightLoanDto;
import com.bank.loans.data.dto.loan.LoanDto;
import com.bank.loans.data.entity.LoanEntity;
import com.bank.loans.data.entity.RateEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@DependsOn("rateMapper")
@RequiredArgsConstructor
public class LoanMapper {
    private final ModelMapper mapper;

    @PostConstruct
    private void configureMapper() {
        mapper.createTypeMap(CreateLoanDto.class, LoanEntity.class)
                .addMapping(source -> false, LoanEntity::setIsClosed)
                .addMapping(CreateLoanDto::getAmount, LoanEntity::setAmountRemain);

        mapper.createTypeMap(LoanEntity.class, LoanDto.class);
        mapper.typeMap(LoanEntity.class, LightLoanDto.class)
                .addMappings(mapper -> {
                    mapper.map(source -> source.getRate().getInterestRate(),
                            LightLoanDto::setRate);
                });
    }

    public LoanEntity toEntity(CreateLoanDto createLonDto, RateEntity rateEntity,
                               Long accountId) {
        if (Objects.nonNull(createLonDto)) {
            LoanEntity loanEntity = mapper.map(createLonDto, LoanEntity.class);
            loanEntity.setRate(rateEntity);
            loanEntity.setAccountId(accountId);
            return loanEntity;
        } else {
            return null;
        }
    }

    public LoanDto toDto(LoanEntity loanEntity) {
        return Objects.isNull(loanEntity) ? null :
                mapper.map(loanEntity, LoanDto.class);
    }

    public LightLoanDto toLightDto(LoanEntity loanEntity) {
        return Objects.isNull(loanEntity) ? null :
                mapper.map(loanEntity, LightLoanDto.class);
    }
}
