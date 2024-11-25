package com.exalt.bank_account.adapter.request;

import lombok.Getter;

@Getter
public enum ResponseStatusEmum {
    OK("Transaction approuved"),
    BAD_REQUEST("An error occurred, please contact your bank"),
    FORBIDDEN("Insufficient funds for withdrawal"),
    CHECKBALANCE("Your remaining balance is : %s");

    private final String value;

    ResponseStatusEmum(String value) {
        this.value = value;
    }
}
