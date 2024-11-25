package com.exalt.bank_account;

import com.exalt.bank_account.adapter.output.BankAccountAdapter;
import com.exalt.bank_account.adapter.output.TransactionAdapter;
import com.exalt.bank_account.application.ports.output.BankAccountRepository;
import com.exalt.bank_account.application.ports.output.TransactionRepository;
import com.exalt.bank_account.domain.model.Account;
import com.exalt.bank_account.domain.model.Transaction;
import com.exalt.bank_account.domain.model.TransactionTypeEnum;
import com.exalt.bank_account.infra.BankAccountEntity;
import com.exalt.bank_account.infra.TransactionEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BankOperationInputAdapteurTest {


    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;


    @ParameterizedTest
    @ValueSource(strings = {"{\"amount\":100, \"userId\":1, \"typeEnum\":\"DEPOSIT\"}"})
    void testTransactionDeposit(String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/user/transaction",
                request,
                String.class
        );
        assertEquals(200, responseEntity.getStatusCodeValue());
    }



    @Test
    void testGetTransactions() {
        ParameterizedTypeReference<List<Transaction>> responseType = new ParameterizedTypeReference<List<Transaction>>() {};
        ResponseEntity<List<Transaction>>
                responseEntity = restTemplate.exchange("http://localhost:" + port + "/user/1/transactions", HttpMethod.GET, null, responseType);
        List<Transaction> response = responseEntity.getBody();
        assert response != null;
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
    

}
