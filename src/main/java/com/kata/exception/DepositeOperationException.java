package com.kata.exception;

public class DepositeOperationException extends RuntimeException {

    public static final String OPERATION_REFESUD = "The deposit operation is refused ";

    public DepositeOperationException() {
        super(OPERATION_REFESUD);
    }
}
