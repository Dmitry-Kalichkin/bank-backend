package com.bank.loans.service.rate;



import com.bank.loans.data.dto.rate.CreateRateDto;
import com.bank.loans.data.dto.rate.LightRateDto;
import com.bank.loans.data.dto.rate.RateDto;

import java.util.List;

public interface RateService {
    void save(CreateRateDto createRateDto);
    List<LightRateDto> getAll();
    RateDto getById(Long id);
    RateDto getByName(String name);
}
