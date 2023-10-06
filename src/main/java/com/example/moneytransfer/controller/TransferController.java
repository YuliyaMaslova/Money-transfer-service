package com.example.moneytransfer.controller;

import com.example.moneytransfer.model.ConfirmOperation;
import com.example.moneytransfer.model.TransferRequest;
import com.example.moneytransfer.model.TransferResponse;
import com.example.moneytransfer.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://serp-ya.github.io", methods = RequestMethod.POST)
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public TransferResponse transfer(@Valid @RequestBody TransferRequest transferRequest) {
        return new TransferResponse(transferService.requestTransfer(transferRequest));
    }

    @PostMapping("/confirmOperation")
    public TransferResponse transferConfirm(@RequestBody ConfirmOperation confirmOperation) {
        return new TransferResponse(transferService.confirmOperation(confirmOperation.operationId(), confirmOperation.code()));
    }
}
