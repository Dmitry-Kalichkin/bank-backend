package com.bank.loans.controller;

import com.bank.loans.service.rating.CreditRatingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/ratings", produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Credit ratings", description = "Endpoints related to credit ratings.")
public class CreditRatingController {
    private final CreditRatingService creditRatingService;

    @GetMapping("/{userId}")
    public Short getRating(@PathVariable UUID userId) {
        return creditRatingService.getRating(userId);
    }
}
