package com.bank.accounts.serivce.repository;




import com.bank.accounts.serivce.data.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends PagingAndSortingRepository<AccountEntity, Long>,
        CrudRepository<AccountEntity, Long> {
    List<AccountEntity> findAllByUserId(UUID userId);
}
