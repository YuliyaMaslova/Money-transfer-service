package com.example.money_transfer_service.controller;

import com.example.money_transfer_service.model.TransferRequest;
import com.example.money_transfer_service.model.TransferResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransferController {

    @PostMapping("/transfer")
    public TransferResponse transfer(@RequestBody TransferRequest transferRequest) {
        System.out.println("request: " + transferRequest);
        return new TransferResponse("12312dfsfs");
    }
}
