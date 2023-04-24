package com.bank.accounts.serivce.service.operation;


import com.bank.accounts.serivce.data.dto.operation.CreateOperationDto;
import com.bank.accounts.serivce.data.dto.operation.LoanPaymentOperationDto;

public interface OperationService {
    void receiveOperation(CreateOperationDto createOperationDto);
    void receiveOperationWithReply(LoanPaymentOperationDto loanPaymentOperationDto);
}