package com.example.exception;
/**
 * Custom exception class for handling cases where a duplicate username is encountered.
 */
public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException(String message) {
       // Calls the constructor of the superclass (RuntimeException) with the provided message.
        super(message);
    }
}

