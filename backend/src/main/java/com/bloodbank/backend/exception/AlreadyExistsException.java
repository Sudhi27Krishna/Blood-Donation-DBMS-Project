package com.bloodbank.backend.exception;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String msg) {
        super(msg);
    }
}
