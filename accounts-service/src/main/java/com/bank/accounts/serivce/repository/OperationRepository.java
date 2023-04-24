package com.bank.accounts.serivce.repository;

import com.bank.accounts.serivce.data.entity.OperationEntity;
import org.springframework.data.repository.CrudRepository;


public interface OperationRepository extends CrudRepository<OperationEntity, Long> {
}
