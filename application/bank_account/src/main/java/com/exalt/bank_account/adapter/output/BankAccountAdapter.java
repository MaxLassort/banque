package com.exalt.bank_account.adapter.output;

import com.exalt.bank_account.application.ports.output.BankAccountRepository;
import com.exalt.bank_account.domain.model.Account;
import com.exalt.bank_account.infra.BankAccountEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Getter
public class BankAccountAdapter implements DomainMapper<Account, BankAccountEntity> {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountAdapter(
            BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BankAccountEntity domainToInfra(Account domainEntity) {
        return new BankAccountEntity(domainEntity.getIdAccount(), domainEntity.getIdUser(), domainEntity.getBalance());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Account infraToDomain(BankAccountEntity infraEntity) {
        return new Account(infraEntity.getId(), infraEntity.getUserId(), infraEntity.getBalance());
    }

    public double checkBalance(Integer userId) throws EntityNotFoundException {
        //TODO save a new transaction ?
        BankAccountEntity
                account =
                this.bankAccountRepository.findByUserId(userId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Account for user %s not found", userId)));
        return account.getBalance();
    }
}
