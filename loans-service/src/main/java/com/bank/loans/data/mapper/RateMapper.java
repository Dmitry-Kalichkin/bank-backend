package com.bank.loans.data.mapper;

import com.bank.loans.data.dto.rate.CreateRateDto;
import com.bank.loans.data.dto.rate.LightRateDto;
import com.bank.loans.data.dto.rate.RateDto;
import com.bank.loans.data.entity.RateEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RateMapper {
    private final ModelMapper mapper;

    @PostConstruct
    private void configureMapper() {
        mapper.createTypeMap(CreateRateDto.class, RateEntity.class);
        mapper.createTypeMap(RateEntity.class, RateDto.class);
        mapper.createTypeMap(RateEntity.class, LightRateDto.class);
    }

    public RateDto toDto(RateEntity rateEntity) {
        return Objects.isNull(rateEntity) ? null :
                mapper.map(rateEntity, RateDto.class);
    }

    public LightRateDto toLightDto(RateEntity rateEntity) {
        return Objects.isNull(rateEntity) ? null :
                mapper.map(rateEntity, LightRateDto.class);
    }

    public RateEntity toEntity(CreateRateDto createRateDto) {
        return Objects.isNull(createRateDto) ? null :
                mapper.map(createRateDto, RateEntity.class);
    }
}
