package com.nnk.springboot.exception;

public class NegativeNumberException extends Exception{
    public NegativeNumberException(String message) {
        super(message);
    }
}
