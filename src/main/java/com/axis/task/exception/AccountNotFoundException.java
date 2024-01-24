package com.axis.task.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message)
    {
        super(message);
    }
}

