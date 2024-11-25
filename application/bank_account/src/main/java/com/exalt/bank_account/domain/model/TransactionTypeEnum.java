package com.exalt.bank_account.domain.model;

/**
 * Enum representing all type of bank transaction.
 */
public enum TransactionTypeEnum {
    DEPOSIT, WHITHDRAW, CHECKBALANCE;

    public static TransactionTypeEnum fromCode(String code){
        for(TransactionTypeEnum emum: TransactionTypeEnum.values()){
            if(code.equals(emum.name())){
                return emum;
            }
        }
            return null;
    }
}
