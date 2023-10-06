package com.example.moneytransfer.controller;

import com.example.moneytransfer.exception.ConfirmationException;
import com.example.moneytransfer.exception.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlingController {
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO error(Exception e) {
        return handleError(e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO error(HttpMessageNotReadableException e) {
        return handleError(e);
    }

    @ExceptionHandler(ConfirmationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO error(ConfirmationException e) {
        return handleError(e);
    }

    private ErrorDTO handleError(Exception ex) {
        log.error("Handle error", ex);
        return new ErrorDTO(ex.getMessage(), 0);
    }
}
