package com.hajji.springbootbasics.exceptions.project;

public class DuplicateAssignmentException extends RuntimeException {
    public DuplicateAssignmentException(String message) {
        super(message);
    }
}
