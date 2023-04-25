package com.bank.loans.service.loan;

import com.bank.loans.data.dto.loan.CreateLoanDto;
import com.bank.loans.data.dto.loan.LightLoanDto;
import com.bank.loans.data.dto.loan.LoanDto;

import java.util.List;
import java.util.UUID;

public interface LoanService {
    LoanDto open(CreateLoanDto createLoanDto);
    void close(Long id);
    List<LightLoanDto> getAllByUser(UUID userId);
    LoanDto getById(Long id);
}
