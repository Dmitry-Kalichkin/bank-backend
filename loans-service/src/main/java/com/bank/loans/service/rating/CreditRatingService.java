package com.bank.loans.service.rating;

import java.util.UUID;

public interface CreditRatingService {
    Short getRating(UUID userId);
    void increaseRating(UUID userId, Short value);
    void decreaseRating(UUID userId, Short value);
}
