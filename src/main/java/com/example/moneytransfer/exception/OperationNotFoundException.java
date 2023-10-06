package com.example.moneytransfer.exception;

import java.util.UUID;

public class OperationNotFoundException extends ConfirmationException {
    public OperationNotFoundException(UUID operationId) {
        super(String.format("Operation with id %s not found", operationId));
    }
}
