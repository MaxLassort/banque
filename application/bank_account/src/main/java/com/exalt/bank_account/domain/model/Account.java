package com.exalt.bank_account.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing a bank account.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Account {
    private Integer idAccount;
    private Integer idUser;
    private double balance;
}
