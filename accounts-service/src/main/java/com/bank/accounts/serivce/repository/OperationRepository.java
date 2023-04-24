package ru.bank.accounts.repository;

import org.springframework.data.repository.CrudRepository;
import ru.bank.accounts.data.entity.OperationEntity;

public interface OperationRepository extends CrudRepository<OperationEntity, Long> {
}
