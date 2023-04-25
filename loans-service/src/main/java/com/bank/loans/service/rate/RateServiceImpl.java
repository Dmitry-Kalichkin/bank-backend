package com.bank.loans.service.rate;

import com.bank.loans.data.dto.rate.CreateRateDto;
import com.bank.loans.data.dto.rate.LightRateDto;
import com.bank.loans.data.dto.rate.RateDto;
import com.bank.loans.data.entity.RateEntity;
import com.bank.loans.data.exception.RateNameExistsException;
import com.bank.loans.data.exception.RateNotExistsException;
import com.bank.loans.data.mapper.RateMapper;
import com.bank.loans.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {
    private final RateRepository rateRepository;
    private final RateMapper rateMapper;

    @Override
    @Transactional
    public void save(CreateRateDto createRateDto) {
        log.info("Manager with id {} saving new rate with name '{}' and interest rate {}",
                createRateDto.getManagerId(), createRateDto.getName(),
                createRateDto.getInterestRate());

        if (rateRepository.existsByName(createRateDto.getName())) {
            throw new RateNameExistsException("such rate already exists");
        }
        RateEntity rateEntity = rateMapper.toEntity(createRateDto);
        rateRepository.save(rateEntity);

        log.info("New rate with name '{}' and interest rate {} saved by manager with id {}",
                createRateDto.getName(), createRateDto.getInterestRate()
                ,createRateDto.getManagerId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LightRateDto> getAll() {
        List<RateEntity> rateEntities = rateRepository.findAll();

        return rateEntities.stream().map(rateMapper::toLightDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RateDto getById(Long id) {
        RateEntity rateEntity = rateRepository.findById(id)
                .orElseThrow(() ->
                        new RateNotExistsException("rate with id " + id +
                                " not found"));

        return rateMapper.toDto(rateEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public RateDto getByName(String name) {
        RateEntity rateEntity = rateRepository.findByName(name)
                .orElseThrow(() ->
                        new RateNotExistsException("rate with name " + name +
                                " not found"));

        return rateMapper.toDto(rateEntity);
    }
}

