package com.example.moneytransfer.model;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TransferRequest(
        @Pattern(regexp = "\\d{16}", message = "Invalid cardFromNumber: Must be 16 characters")
        String cardFromNumber,
        String cardFromValidTill,
        @Size(min = 3, max = 3, message = "Invalid cardFromCVV: Must be 3 characters")
        String cardFromCVV,
        @Pattern(regexp = "\\d{16}", message = "Invalid cardToNumber: Must be 16 characters")
        String cardToNumber,
        Amount amount
) {
}
