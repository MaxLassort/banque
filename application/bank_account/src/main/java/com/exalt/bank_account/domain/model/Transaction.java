package com.exalt.bank_account.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing a bank transaction.
 * This class contains details of a transaction, such as its transaction ID, the associated user ID,
 * the transaction type (deposit, withdrawal, etc.), and any additional details.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    private Integer idTransaction;
    private Integer idUser;
    private TransactionTypeEnum transactionType;
    private String details;
    private Account account;


}
