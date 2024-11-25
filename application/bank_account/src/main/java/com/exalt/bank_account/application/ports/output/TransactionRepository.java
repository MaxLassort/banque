package com.exalt.bank_account.application.ports.output;

import com.exalt.bank_account.infra.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Secondary Adapter use to manage transaction table in bdd
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
    List<TransactionEntity> findAllByBankAccountEntity_UserId(Integer userId);

}
