package com.example.moneytransfer.service;

import com.example.moneytransfer.exception.ConfirmationCodeExpiredException;
import com.example.moneytransfer.exception.WrongCodeException;
import com.example.moneytransfer.model.Amount;
import com.example.moneytransfer.model.TransferRequest;
import com.example.moneytransfer.repository.TransferOperation;
import com.example.moneytransfer.repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferServiceTest {

    private TransferService transferService;

    @Mock
    private TransferRepository transferRepository;

    private static final Duration CONFIRMATION_CODE_LIFETIME = Duration.ofMinutes(10);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transferService = new TransferService(transferRepository, CONFIRMATION_CODE_LIFETIME);
    }

    @Test
    void requestTransfer_ShouldCreateOperationAndReturnOperationId() {
        TransferRequest transferRequest = new TransferRequest(
                "1234567890123456",
                "1225",
                "123",
                "9876543210987654",
                new Amount(1000, "USD")
        );
        String code = "0000";

        when(transferRepository.createOperation(any(UUID.class), any(String.class), any(TransferRequest.class)))
                .thenReturn(mock(TransferOperation.class));

        transferService.requestTransfer(transferRequest);

        verify(transferRepository, times(1)).createOperation(any(UUID.class), eq(code), eq(transferRequest));
        verifyNoMoreInteractions(transferRepository);
    }

    @Test
    void confirmOperation_WithValidCode_ShouldDeleteOperationAndReturnOperationId() {
        UUID operationId = UUID.randomUUID();
        String code = "0000";
        LocalDateTime createdAt = LocalDateTime.now().minusMinutes(5);

        TransferRequest transferRequest = new TransferRequest(
                "1234567890123456",
                "1225",
                "123",
                "9876543210987654",
                new Amount(1000, "USD")
        );
        TransferOperation operation = new TransferOperation(transferRequest, createdAt, code);

        when(transferRepository.getOperation(operationId)).thenReturn(Optional.of(operation));
        doNothing().when(transferRepository).deleteOperation(operationId);

        UUID result = transferService.confirmOperation(operationId, code);

        assertEquals(operationId, result);
        verify(transferRepository, times(1)).getOperation(operationId);
        verify(transferRepository, times(1)).deleteOperation(operationId);
        verifyNoMoreInteractions(transferRepository);
    }

    @Test
    void confirmOperation_WithExpiredCode_ShouldThrowConfirmationCodeExpiredException() {
        UUID operationId = UUID.randomUUID();
        String code = "0000";
        LocalDateTime createdAt = LocalDateTime.now().minus(CONFIRMATION_CODE_LIFETIME).minusMinutes(1);

        TransferRequest transferRequest = new TransferRequest(
                "1234567890123456",
                "1225",
                "123",
                "9876543210987654",
                new Amount(1000, "USD")
        );
        TransferOperation operation = new TransferOperation(transferRequest, createdAt, code);

        when(transferRepository.getOperation(operationId)).thenReturn(Optional.of(operation));

        Exception exception = assertThrows(ConfirmationCodeExpiredException.class,
                () -> transferService.confirmOperation(operationId, code));

        assertEquals(String.format("Operation %s has been expired", operationId), exception.getMessage());

        verify(transferRepository, times(1)).getOperation(operationId);
        verifyNoMoreInteractions(transferRepository);
    }

    @Test
    void confirmOperation_WithWrongCode_ShouldThrowWrongCodeException() {
        UUID operationId = UUID.randomUUID();
        String code = "1111";
        LocalDateTime createdAt = LocalDateTime.now().minusMinutes(5);

        TransferRequest transferRequest = new TransferRequest(
                "1234567890123456",
                "1225",
                "123",
                "9876543210987654",
                new Amount(1000, "USD")
        );
        TransferOperation operation = new TransferOperation(transferRequest, createdAt, "0000");

        when(transferRepository.getOperation(operationId)).thenReturn(Optional.of(operation));

        Exception exception = assertThrows(WrongCodeException.class,
                () -> transferService.confirmOperation(operationId, code));

        assertEquals("Wrong confirmation code for operation " + operationId, exception.getMessage());

        verify(transferRepository, times(1)).getOperation(operationId);
        verifyNoMoreInteractions(transferRepository);
    }
}