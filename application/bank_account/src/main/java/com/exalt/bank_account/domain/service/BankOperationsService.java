package com.exalt.bank_account.domain.service;

import com.exalt.bank_account.adapter.output.BankAccountAdapter;
import com.exalt.bank_account.adapter.output.TransactionAdapter;
import com.exalt.bank_account.application.ports.input.BankOperationsPort;
import com.exalt.bank_account.domain.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of port for bank operations.
 */
@Service
public class BankOperationsService implements BankOperationsPort {

    private final BankAccountAdapter bankAccountAdapter;
    private final TransactionAdapter transactionAdapter;

    public BankOperationsService(BankAccountAdapter bankAccountAdapter, TransactionAdapter transactionAdapter) {
        this.bankAccountAdapter = bankAccountAdapter;
        this.transactionAdapter = transactionAdapter;
    }

    /**
     * Deposits the specified amount into the account associated with the given user ID.
     *
     * @param amount The amount to deposit.
     * @param userId The ID of the user whose account is being deposited into.
     * @return {@code true} if the deposit was successful, {@code false} otherwise.
     */
    @Override
    public boolean deposit(double amount, Integer userId) {
        return this.transactionAdapter.deposit(amount, userId);
    }

    /**
     * Withdraws the specified amount from the account associated with the given user ID.
     *
     * @param amount The amount to withdraw.
     * @param userId The ID of the user whose account is being withdrawn from.
     * @return {@code true} if the withdrawal was successful, {@code false} otherwise.
     */
    @Override
    public boolean withdraw(double amount, Integer userId) {
        return this.transactionAdapter.withdraw(amount, userId);
    }

    /**
     * Checks the balance of the account associated with the given user ID.
     *
     * @param userId The ID of the user whose account balance is being checked.
     * @return The balance of the user's account.
     */
    @Override
    public double checkBalance(Integer userId) {
        return this.bankAccountAdapter.checkBalance(userId);
    }

    /**
     * Retrieves the list of transactions associated with the given user ID.
     *
     * @param userId The ID of the user whose transactions are being retrieved.
     * @return The list of transactions associated with the user.
     */
    @Override
    public List<Transaction> getTransactions(Integer userId) {
        return this.transactionAdapter.findAllByUserId(userId);
    }
}
