package com.kata.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String msg) {
        super("The customer was not found.");
    }
}
