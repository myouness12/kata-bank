package com.kata.exception;

public class WithdrawalOperationException extends RuntimeException {
    public static final String INSUFFICIENT_CREDIT = "Insufficient credit ";

    public WithdrawalOperationException() {
        super(INSUFFICIENT_CREDIT);
    }
}
