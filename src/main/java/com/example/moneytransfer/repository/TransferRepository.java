package com.example.moneytransfer.repository;

import com.example.moneytransfer.model.TransferRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class TransferRepository {
    private final ConcurrentMap<UUID, TransferOperation> operations = new ConcurrentHashMap<>();

    public TransferOperation createOperation(UUID operationId, String code, TransferRequest transferRequest) {
        TransferOperation operation = new TransferOperation(transferRequest, LocalDateTime.now(), code);
        operations.put(operationId, operation);
        return operation;
    }

    public Optional<TransferOperation> getOperation(UUID operationId) {
        return Optional.ofNullable(operations.get(operationId));
    }

    public void deleteOperation(UUID operationId) {
        operations.remove(operationId);
    }
}
