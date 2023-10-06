package com.example.moneytransfer.service;

import com.example.moneytransfer.exception.ConfirmationCodeExpiredException;
import com.example.moneytransfer.exception.OperationNotFoundException;
import com.example.moneytransfer.exception.WrongCodeException;
import com.example.moneytransfer.model.TransferRequest;
import com.example.moneytransfer.repository.TransferOperation;
import com.example.moneytransfer.repository.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransferService {
    private static final Logger log = LoggerFactory.getLogger(TransferService.class);
    private final TransferRepository transferRepository;
    private final Duration confirmationCodeLifetime;

    public TransferService(TransferRepository transferRepository,
                           @Value("${confirmation-code.lifetime}") Duration confirmationCodeLifetime) {
        this.transferRepository = transferRepository;
        this.confirmationCodeLifetime = confirmationCodeLifetime;
    }

    public UUID requestTransfer(TransferRequest transferRequest) {
        UUID operationId = UUID.randomUUID();
//        String code = String.format("%04d", new Random().nextInt(10000));
        String code = "0000";
        log.info("send code to client: {}", code);
        transferRepository.createOperation(operationId, code, transferRequest);
        log.info("Created transfer operation {} with request: {}", operationId, transferRequest);
        return operationId;
    }

    public UUID confirmOperation(UUID operationId, String code) {
        TransferOperation operation = transferRepository.getOperation(operationId)
                .orElseThrow(() -> new OperationNotFoundException(operationId));

        if (operation.createdAt().plus(confirmationCodeLifetime).isBefore(LocalDateTime.now())) {
            throw new ConfirmationCodeExpiredException(operationId);
        }

        if (!operation.code().equals(code)) {
            throw new WrongCodeException(operationId);
        }
        transferRepository.deleteOperation(operationId);
        log.info("Operation {} was completed", operationId);

        return operationId;
    }
}
