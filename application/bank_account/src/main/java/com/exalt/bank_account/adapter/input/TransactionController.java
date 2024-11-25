package com.exalt.bank_account.adapter.input;

import com.exalt.bank_account.adapter.request.Request;
import com.exalt.bank_account.adapter.request.ResponseStatusEmum;
import com.exalt.bank_account.application.ports.input.BankOperationsPort;
import com.exalt.bank_account.domain.model.Transaction;
import com.exalt.bank_account.domain.model.TransactionTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user")
@Slf4j
public class TransactionController {
    BankOperationsPort bankOperationsPort;

    public TransactionController(BankOperationsPort bankOperationsPort) {
        this.bankOperationsPort = bankOperationsPort;
    }

    @PostMapping("/transaction")
    @Operation(summary = "Perform a transaction",
            description = "Endpoint to initiate a transaction operation, such as deposit or withdrawal, for a user account.")
    ResponseEntity<String> depositController(@RequestBody Request request) {
        TransactionTypeEnum typeEnum = TransactionTypeEnum.fromCode(request.typeEnum());
        if (typeEnum == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseStatusEmum.BAD_REQUEST.getValue());
        }
        try {
            boolean response;
            switch (typeEnum) {
                case DEPOSIT -> {
                    response = this.bankOperationsPort.deposit(request.amount(), request.userId());
                    if (!response) {
                        //TODO implement this case
                        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(ResponseStatusEmum.BAD_REQUEST.getValue());
                    }
                }
                case WHITHDRAW -> {
                    response = this.bankOperationsPort.withdraw(request.amount(), request.userId());
                    if (!response) {
                        //TODO implement this case
                        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(ResponseStatusEmum.FORBIDDEN.getValue());
                    }
                }
                case CHECKBALANCE -> {
                    double balance = this.bankOperationsPort.checkBalance(request.userId());
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(String.format(ResponseStatusEmum.CHECKBALANCE.getValue(), balance));

                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ResponseStatusEmum.BAD_REQUEST.getValue());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseStatusEmum.BAD_REQUEST.getValue());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseStatusEmum.OK.getValue());
    }

    @GetMapping("/{idUser}/transactions")
    @Operation(summary = "Get all transactions",
            description = "Get all informations about transactions did by user")
    ResponseEntity<List<Transaction>> getAllTransactionsController(@PathVariable Integer idUser) {
        List<Transaction> transactionList = new ArrayList<>();
        try {
            transactionList = this.bankOperationsPort.getTransactions(idUser);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionList);
    }


}
