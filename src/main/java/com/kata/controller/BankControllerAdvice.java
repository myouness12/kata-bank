package com.kata.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.kata.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BankControllerAdvice {

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String AccountNotFoundHandler(AccountNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String CustommerNotFoundHandler(CustomerNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(NoOperationFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String NoOperationFoundHandler(NoOperationFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DepositeOperationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String DepositeOperationHandler(DepositeOperationException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(WithdrawalOperationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String WithdrawalOperationHandler(WithdrawalOperationException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errorsMap = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorsMap.put(fieldName, errorMessage);
        });
        return errorsMap;
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> invalidFormatException(final InvalidFormatException e) {
        return new ResponseEntity("Operation refused ", HttpStatus.UNPROCESSABLE_ENTITY);
    }




}
