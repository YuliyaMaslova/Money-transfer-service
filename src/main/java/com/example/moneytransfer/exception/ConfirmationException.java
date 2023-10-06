package com.example.moneytransfer.exception;

public abstract class ConfirmationException extends RuntimeException {
    public ConfirmationException(String message) {
        super(message);
    }
}
