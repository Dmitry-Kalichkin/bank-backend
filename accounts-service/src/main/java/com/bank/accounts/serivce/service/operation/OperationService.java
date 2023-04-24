package ru.bank.accounts.service.operation;


import ru.bank.accounts.data.dto.operation.CreateOperationDto;
import ru.bank.accounts.data.dto.operation.LoanPaymentOperationDto;

public interface OperationService {
    void receiveOperation(CreateOperationDto createOperationDto);
    void receiveOperationWithReply(LoanPaymentOperationDto loanPaymentOperationDto);
}