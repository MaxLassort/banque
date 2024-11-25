package com.exalt.bank_account.application.ports.input;

import com.exalt.bank_account.domain.model.Transaction;

import java.util.List;
/**
 * Interface representing the port for bank operations.
 */
public interface BankOperationsPort {
    boolean deposit(double amount, Integer userId);
    boolean withdraw(double amount, Integer usderId);
    double checkBalance(Integer userId);
    List<Transaction> getTransactions(Integer userId);
}
