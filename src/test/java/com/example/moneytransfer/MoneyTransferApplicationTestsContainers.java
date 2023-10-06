package com.example.moneytransfer;

import com.example.moneytransfer.model.Amount;
import com.example.moneytransfer.model.ConfirmOperation;
import com.example.moneytransfer.model.TransferRequest;
import com.example.moneytransfer.model.TransferResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MoneyTransferApplicationTestsContainers {
    private final static int PORT = 5500;

    @Autowired
    TestRestTemplate restTemplate;

    @Container
    public static GenericContainer<?> myapp = new GenericContainer<>("moneytransfer:latest")
            .withExposedPorts(PORT);

    @Test
    void moneyTransferTest() throws JsonProcessingException {
        TransferRequest request = new TransferRequest(
                "1115666600101892",
                "05/23",
                "530",
                "5555636200001111",
                new Amount(67899, "RUB")
        );

        ResponseEntity<String> forEntity = restTemplate.postForEntity(
                "http://localhost:" + myapp.getMappedPort(PORT) + "/transfer", request, String.class);

        TransferResponse transferResponse = new ObjectMapper().readValue(forEntity.getBody(), TransferResponse.class);
        Assertions.assertNotNull(transferResponse.operationId());
    }

   @Test
    void confirmOperationTest() {
       UUID operationId = UUID.randomUUID();
       ConfirmOperation request = new ConfirmOperation(operationId,"0000");

       ResponseEntity<String> forEntity = restTemplate.postForEntity(
               "http://localhost:" + myapp.getMappedPort(PORT) + "/confirmOperation", request, String.class);
       String expected = String.format("{\"message\":\"Operation with id %s not found\",\"id\":0}", operationId);
       String actual = forEntity.getBody();
       Assertions.assertEquals(expected, actual);
    }

}
