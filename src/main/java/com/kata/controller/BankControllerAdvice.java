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

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BankControllerAdvice {

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String accountNotFoundHandler(AccountNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String custommerNotFoundHandler(CustomerNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(NoOperationFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String noOperationFoundHandler(NoOperationFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DepositeOperationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String depositeOperationHandler(DepositeOperationException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(WithdrawalOperationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String withdrawalOperationHandler(WithdrawalOperationException ex) {
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

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("Not valid operation " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> invalidFormatException(final InvalidFormatException e) {
        return new ResponseEntity<>("Not valid operation ", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> numberFormatException(final NumberFormatException e) {
        return new ResponseEntity<>("Not valid operation", HttpStatus.BAD_REQUEST);
    }



}
