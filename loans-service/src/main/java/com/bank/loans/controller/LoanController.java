package com.bank.loans.controller;

import com.bank.commons.exception.ExceptionResponse;
import com.bank.loans.data.dto.loan.CreateLoanDto;
import com.bank.loans.data.dto.loan.LightLoanDto;
import com.bank.loans.data.dto.loan.LoanDto;
import com.bank.loans.data.exception.UserManagerSameIdException;
import com.bank.loans.service.loan.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Loans", description = "Endpoints related to loans.")
public class LoanController {
    private final LoanService loanService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Loan successfully opened",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Invalid data provided." +
                            " See CreateLoanDto and notice that" +
                            " manager can;t give loan to himself",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500",
                    description = "Unknown error on server",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))) })
    @Operation(summary = "Open new loan")
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanDto open(@RequestBody @Valid CreateLoanDto createLoanDto) {
        if (createLoanDto.getManagerId().equals(createLoanDto.getUserId())) {
            throw new UserManagerSameIdException("manager can't give loan to himself",
                    "Manager " + createLoanDto.getManagerId() +
                            " tried to give loan to himself"
            );
        }
        return loanService.open(createLoanDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Loan successfully closed",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Invalid id data type provided." +
                            " Ensure to provide id type 'long' ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404",
                    description = "Loan with such id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500",
                    description = "Unknown error on server",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))) })
    @Operation(summary = "Close loan")
    @PatchMapping("/close/{id}")
    public void close(@PathVariable Long id) {
        loanService.close(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Loans found and returned"),
            @ApiResponse(responseCode = "400",
                    description = "Invalid user id data type provided." +
                            " Ensure that you provide id type 'uuid' ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500",
                    description = "Unknown error on server",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))) })
    @Operation(summary = "Get loans related to particular user")
    @GetMapping(value = "/user/{id}")
    public List<LightLoanDto> GetAllByUser(@PathVariable UUID id) {
        return loanService.getAllByUser(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Loan found and returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoanDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid id data type provided." +
                            " Ensure that you provide id type 'long'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404",
                    description = "Loan with such id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500",
                    description = "Unknown error on server",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))) })
    @Operation(summary = "Get full loan data by loan's id")
    @GetMapping("/{id}")
    public LoanDto getById(@PathVariable Long id) {
        return loanService.getById(id);
    }
}
