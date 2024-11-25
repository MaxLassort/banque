package com.exalt.bank_account.adapter.output;

import com.exalt.bank_account.application.ports.output.BankAccountRepository;
import com.exalt.bank_account.application.ports.output.TransactionRepository;
import com.exalt.bank_account.domain.model.Account;
import com.exalt.bank_account.domain.model.Transaction;
import com.exalt.bank_account.domain.model.TransactionTypeEnum;
import com.exalt.bank_account.infra.BankAccountEntity;
import com.exalt.bank_account.infra.TransactionEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Slf4j
public class TransactionAdapter implements DomainMapper<Transaction, TransactionEntity> {
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountAdapter bankAccountAdapter;

    public TransactionAdapter(
            TransactionRepository transactionRepository,
            BankAccountRepository bankAccountRepository,
            BankAccountAdapter bankAccountAdapter) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountAdapter = bankAccountAdapter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionEntity domainToInfra(Transaction domainEntity) {
        BankAccountEntity account = this.bankAccountAdapter.domainToInfra(domainEntity.getAccount());
        return new TransactionEntity(
                domainEntity.getIdTransaction(),
                domainEntity.getDetails(),
                domainEntity.getTransactionType(),
                account
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction infraToDomain(TransactionEntity infraEntity) {
        Account account = this.bankAccountAdapter.infraToDomain(infraEntity.getBankAccountEntity());
        return new Transaction(
                infraEntity.getId(),
                account.getIdUser(),
                infraEntity.getType(),
                infraEntity.getDetails(),
                account
        );
    }

    /**
     * Deposits the specified amount into the bank account associated with the given user ID.
     * If the bank account is not found, throws EntityNotFoundException.
     * Saves the updated balance and creates a deposit transaction.
     *
     * @param amount The amount to deposit.
     * @param userId The ID of the user whose bank account is being deposited into.
     * @return {@code true} if the deposit was successful, {@code false} otherwise.
     */
    @Transactional
    public boolean deposit(double amount, Integer userId) {
        BankAccountEntity
                bankAccountEntity =
                this.bankAccountRepository.findByUserId(userId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format(
                                "Bank account with id %s not found",
                                userId
                        )));
        String
                detail =
                String.format(
                        "Transaction de type %s, pour un montant de %s",
                        TransactionTypeEnum.DEPOSIT.name(),
                        amount
                );
        double newBanlance = bankAccountEntity.getBalance() + amount;
        bankAccountEntity.setBalance(newBanlance);
        try {
            this.bankAccountRepository.save(bankAccountEntity);
            TransactionEntity
                    transactionEntity =
                    new TransactionEntity(detail, TransactionTypeEnum.DEPOSIT, bankAccountEntity);
            this.transactionRepository.save(transactionEntity);
        } catch (Exception e) {
            log.error("Error during deposit: amount = {}, userId = {}", amount, userId, e);
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Withdraws the specified amount from the bank account associated with the given user ID.
     * If the bank account is not found or the balance is insufficient, returns {@code false}.
     * Saves the updated balance and creates a withdrawal transaction.
     *
     * @param amount The amount to withdraw.
     * @param userId The ID of the user whose bank account is being withdrawn from.
     * @return {@code true} if the withdrawal was successful, {@code false} otherwise.
     */
    @Transactional
    public boolean withdraw(double amount, Integer userId) {
        BankAccountEntity
                bankAccountEntity =
                this.bankAccountRepository.findByUserId(userId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format(
                                "Bank account with id %s not found",
                                userId
                        )));
        String
                detail =
                String.format(
                        "Transaction de type %s, pour un montant de %s",
                        TransactionTypeEnum.WHITHDRAW.name(),
                        amount
                );
        if (bankAccountEntity.getBalance() < amount || bankAccountEntity.getBalance() <= 0) {
            log.error("Transaction cancelled, balance negative");
            return false;
            // TODO save the transaction when it failed but not the balance
        }
        double newBanlance = bankAccountEntity.getBalance() - amount;
        bankAccountEntity.setBalance(newBanlance);
        try {
            this.bankAccountRepository.save(bankAccountEntity);
            TransactionEntity
                    transactionEntity =
                    new TransactionEntity(detail, TransactionTypeEnum.WHITHDRAW, bankAccountEntity);
            this.transactionRepository.save(transactionEntity);
        } catch (Exception e) {
            log.error("Error during deposit: amount = {}, userId = {}", amount, userId, e);
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Retrieves all transactions associated with the bank account of the specified user ID.
     * Uses the userId to find transactions in the repository,
     * then maps each transaction from infrastructure to domain representation.
     *
     * @param userId The ID of the user whose transactions are being retrieved.
     * @return A list of transactions associated with the user's bank account.
     */
    public List<Transaction> findAllByUserId(Integer userId) {

        return this.transactionRepository.findAllByBankAccountEntity_UserId(userId)
                .stream()
                .map(this::infraToDomain)
                .toList();
    }
}
