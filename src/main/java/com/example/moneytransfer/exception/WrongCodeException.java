package com.example.moneytransfer.exception;

import java.util.UUID;

public class WrongCodeException extends ConfirmationException {
    public WrongCodeException(UUID operationId) {
        super(String.format("Wrong confirmation code for operation %s", operationId));
    }
}
