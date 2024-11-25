package com.exalt.bank_account;

import com.exalt.bank_account.adapter.output.BankAccountAdapter;
import com.exalt.bank_account.adapter.output.TransactionAdapter;
import com.exalt.bank_account.domain.model.Account;
import com.exalt.bank_account.domain.model.Transaction;
import com.exalt.bank_account.domain.model.TransactionTypeEnum;
import com.exalt.bank_account.domain.service.BankOperationsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@SpringBootTest
class BankOperationsPortTest {


    static BankAccountAdapter bankAccountAdapter;
    static TransactionAdapter transactionAdapter;

    @BeforeAll
    static void init() {
        bankAccountAdapter = mock(BankAccountAdapter.class);
        transactionAdapter = mock(TransactionAdapter.class);
        List<Transaction>
                list =
                List.of(new Transaction(1,
                                        1,
                                        TransactionTypeEnum.DEPOSIT,
                                        TransactionTypeEnum.DEPOSIT.name(),
                                        new Account()
                ));
        lenient().when(transactionAdapter.deposit(100, 1)).thenReturn(true);
        lenient().when(transactionAdapter.withdraw(100,1)).thenReturn(true);
        lenient().when(bankAccountAdapter.checkBalance(1)).thenReturn(100.0);
        lenient().when(transactionAdapter.findAllByUserId(1)).thenReturn(list);
    }

    @Test
    void testDeposit() {
        BankOperationsService service = new BankOperationsService(bankAccountAdapter, transactionAdapter);
        double amount = 100;
        Integer userId = 1;
        // Verify that the deposit method was called with the correct amount and user id
        Assertions.assertTrue(service.deposit(amount, userId));
    }

    @Test
    void testWhithdraw() {
        BankOperationsService service = new BankOperationsService(bankAccountAdapter, transactionAdapter);
        double amount = 100;
        Integer userId = 1;
        // Verify that the withdraw method was called with the correct amount and user id
        Assertions.assertTrue(service.withdraw(amount, userId));

    }

    @Test
    void testGetTransactions() {
        BankOperationsService service = new BankOperationsService(bankAccountAdapter, transactionAdapter);
        Integer userId = 1;
        // Verify that the getTransaction method was called with the correct user id
        Assertions.assertEquals(1, service.getTransactions(userId).get(0).getIdUser());
    }

    @Test
    void testCheckBalance() {
        BankOperationsService service = new BankOperationsService(bankAccountAdapter, transactionAdapter);
        Integer userId = 1;
        // Verify that the checkBalance method was called with the correct user id
        service.checkBalance(userId);
        Assertions.assertEquals(100.0,service.checkBalance(1));

    }

}
