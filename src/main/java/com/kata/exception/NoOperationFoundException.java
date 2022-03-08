package com.kata.exception;

public class NoOperationFoundException extends RuntimeException {

public static final String NO_OPERATION_FOUND = "No operation found.";
	
    public NoOperationFoundException() {
		super(NO_OPERATION_FOUND);
    }
	
	
	
}
