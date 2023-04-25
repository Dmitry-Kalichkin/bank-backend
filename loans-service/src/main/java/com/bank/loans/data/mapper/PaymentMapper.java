package com.bank.loans.data.mapper;

import com.bank.loans.data.dto.payment.PaymentDto;
import com.bank.loans.data.entity.PaymentEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PaymentMapper {
    private final ModelMapper mapper;

    @PostConstruct
    private void configureMapper() {
        mapper.createTypeMap(PaymentEntity.class, PaymentDto.class);
    }

    public PaymentDto toDto(PaymentEntity paymentEntity) {
        return Objects.isNull(paymentEntity) ? null :
                mapper.map(paymentEntity, PaymentDto.class);
    }
}
