package com.bank.loans.service.rating;

import com.bank.loans.data.entity.CreditRatingEntity;
import com.bank.loans.repository.CreditRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditRatingServiceImpl implements CreditRatingService {
    private final CreditRatingRepository creditRatingRepository;

    @Override
    @Transactional
    public Short getRating(UUID userId) {
        Optional<CreditRatingEntity> creditRatingEntity = creditRatingRepository.findById(userId);

        if (creditRatingEntity.isPresent()) {
            return creditRatingEntity.get().getValue();
        } else {
            return creditRatingRepository
                    .save(new CreditRatingEntity(userId, (short) 101)).getValue();
        }
    }

    @Override
    @Transactional
    public void increaseRating(UUID userId, Short value) {
        CreditRatingEntity creditRatingEntity = creditRatingRepository
                .findById(userId).orElse(creditRatingRepository
                        .save(new CreditRatingEntity(userId, (short) 100)));

        creditRatingEntity.setValue((short) (creditRatingEntity.getValue() + value));
    }

    @Override
    @Transactional
    public void decreaseRating(UUID userId, Short value) {
        CreditRatingEntity creditRatingEntity = creditRatingRepository
                .findById(userId).orElse(creditRatingRepository
                        .save(new CreditRatingEntity(userId, (short) 100)));

        creditRatingEntity.setValue((short) (creditRatingEntity.getValue() - value));
    }

}
