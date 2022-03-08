package com.kata.exception;

public class AccountNotFoundException extends RuntimeException {

    public static final String  ACCOUNT_NOT_FOUND = "The account was not found.";

    public AccountNotFoundException() {
        super(ACCOUNT_NOT_FOUND);
    }
}
