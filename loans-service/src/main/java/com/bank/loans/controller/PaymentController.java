package com.bank.loans.controller;

import com.bank.commons.dto.PageDto;
import com.bank.loans.data.dto.payment.PaymentDto;
import com.bank.loans.service.payment.PaymentService;
import com.bank.loans.util.PageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Endpoints related to payments." +
        " Notice that you can't save or update payment on your own.")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public PaymentDto getById(@PathVariable Long id) {
        return paymentService.getById(id);
    }

    @GetMapping("/")
    public PageDto<PaymentDto> getPage(@RequestParam @NotNull @Min(value = 1, message = "loan id can't be less then 1") Long loanId,
                                       @RequestParam @Min(value = 0, message = "page number can not be less then 0") final Integer page,
                                       @RequestParam @Min(value = 0, message = "size can not be less then 0") final Integer size,
                                       @RequestParam(required = false) Boolean isAscending, @RequestParam(required = false) @Nullable final String... sortParams) {
        Pageable pageRequest = PageUtils.create(page, size, isAscending, sortParams);
        return paymentService.getPage(loanId, pageRequest);
    }

    @GetMapping("/overdue")
    public PageDto<PaymentDto> getPageOverDue(@RequestParam @NotNull @Min(value = 1, message = "loan id can't be less then 1") Long loanId,
                                      @RequestParam @Min(value = 0, message = "page number can not be less then 0") final Integer page,
                                      @RequestParam @Min(value = 0, message = "size can not be less then 0") final Integer size,
                                      @RequestParam(required = false) Boolean isAscending, @RequestParam(required = false) @Nullable final String... sortParams) {
        Pageable pageRequest = PageUtils.create(page, size, isAscending, sortParams);
        return paymentService.getAllOverduedByLoanId(loanId, pageRequest);
    }
}