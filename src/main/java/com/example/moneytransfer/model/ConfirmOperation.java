package com.example.moneytransfer.model;

import java.util.UUID;

public record ConfirmOperation(UUID operationId, String code) {
}
