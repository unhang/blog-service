package com.hang.blogservice.exception;

public class AccountExistsException extends RuntimeException {
    public AccountExistsException(String message) {
        super(message);
    }
}
