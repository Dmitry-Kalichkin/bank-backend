package com.bank.loans.service.payment;

import com.bank.commons.dto.PageDto;
import com.bank.loans.data.dto.operation.OperationResponseDto;
import com.bank.loans.data.dto.payment.PaymentDto;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    PaymentDto getById(Long id);
    PageDto<PaymentDto> getPage(Long loanId, Pageable page);
    PageDto<PaymentDto> getAllOverduedByLoanId(Long loanId, Pageable page);
    void receiveReplies(OperationResponseDto operationResponseDto);
}
