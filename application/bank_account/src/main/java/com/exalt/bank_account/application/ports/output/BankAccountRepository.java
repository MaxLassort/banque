package com.exalt.bank_account.application.ports.output;

import com.exalt.bank_account.infra.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Secondary Adapter use to manage bank_account table in bdd
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Integer> {
    Optional<BankAccountEntity> findByUserId(Integer userId);
}
