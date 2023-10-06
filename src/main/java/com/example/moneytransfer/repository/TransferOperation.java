package com.example.moneytransfer.repository;

import com.example.moneytransfer.model.TransferRequest;

import java.time.LocalDateTime;

public record TransferOperation(
        TransferRequest transferRequest,
        LocalDateTime createdAt,
        String code
) {
}
