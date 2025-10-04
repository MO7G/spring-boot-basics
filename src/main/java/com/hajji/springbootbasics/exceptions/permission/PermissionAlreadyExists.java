package com.hajji.springbootbasics.exceptions.permission;

public class PermissionAlreadyExists extends RuntimeException {
    public PermissionAlreadyExists(String message) {
        super(message);
    }
}
