package com.bank.loans.controller;

import com.bank.commons.exception.ExceptionResponse;
import com.bank.loans.data.dto.loan.LoanDto;
import com.bank.loans.data.dto.rate.CreateRateDto;
import com.bank.loans.data.dto.rate.LightRateDto;
import com.bank.loans.data.dto.rate.RateDto;
import com.bank.loans.service.rate.RateService;
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

@RestController
@RequestMapping(value = "/rates", produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Rates", description = "Endpoints related to rates. " +
        "Rate is a part of loan that describes how loan should be increased.")
public class RateController {
    private final RateService rateService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Rate successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Invalid data provided." +
                            " See CreateRateDto and notice that each rate name is unique",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500",
                    description = "Unknown error on server",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))) })
    @Operation(summary = "Create new rate")
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid CreateRateDto createRateDto) {
        rateService.save(createRateDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Rates returned"),
            @ApiResponse(responseCode = "500",
                    description = "Unknown error on server",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))) })
    @Operation(summary = "Get accessible rates")
    @GetMapping("/")
    public List<LightRateDto> getAll() {
        return rateService.getAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Rate found and returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoanDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid id data type provided." +
                            " Ensure that you provide id type 'long'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404",
                    description = "Rate with such id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500",
                    description = "Unknown error on server",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))) })
    @Operation(summary = "Get rate details by id")
    @GetMapping("/{id}")
    public RateDto getById(@PathVariable Long id) {
        return rateService.getById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Rate found and returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoanDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid data provided",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404",
                    description = "Rate with such name not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500",
                    description = "Unknown error on server",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))) })
    @Operation(summary = "Get rate details by name")
    @GetMapping("/name/{name}")
    public RateDto getByName(@PathVariable String name) {
        return rateService.getByName(name);
    }
}

