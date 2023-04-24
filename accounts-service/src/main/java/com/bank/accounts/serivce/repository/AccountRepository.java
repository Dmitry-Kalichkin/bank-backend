package ru.bank.accounts.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.bank.accounts.data.entity.AccountEntity;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends PagingAndSortingRepository<AccountEntity, Long>,
        CrudRepository<AccountEntity, Long> {
    List<AccountEntity> findAllByUserId(UUID userId);
}
