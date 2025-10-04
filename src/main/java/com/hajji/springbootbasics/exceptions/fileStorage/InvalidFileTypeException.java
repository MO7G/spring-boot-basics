package com.hajji.springbootbasics.exceptions.fileStorage;

public class InvalidFileTypeException extends RuntimeException {
    public InvalidFileTypeException(String message) {
        super(message);
    }
}
