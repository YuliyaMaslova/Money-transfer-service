package com.example.moneytransfer.exception;

import java.util.UUID;

public class ConfirmationCodeExpiredException extends ConfirmationException {
    public ConfirmationCodeExpiredException(UUID operationId) {
        super(String.format("Operation %s has been expired", operationId));
    }
}
