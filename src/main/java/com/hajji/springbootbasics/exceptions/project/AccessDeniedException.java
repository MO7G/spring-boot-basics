package com.hajji.springbootbasics.exceptions.project;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
