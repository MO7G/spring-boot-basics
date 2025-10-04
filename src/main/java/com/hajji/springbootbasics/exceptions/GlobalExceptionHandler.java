package com.hajji.springbootbasics.exceptions;


import com.hajji.springbootbasics.dto.response.ApiError;
import com.hajji.springbootbasics.exceptions.fileStorage.InvalidFileTypeException;
import com.hajji.springbootbasics.exceptions.permission.PermissionAlreadyExists;
import com.hajji.springbootbasics.exceptions.role.RoleAlreadyExistsException;
import com.hajji.springbootbasics.exceptions.user.EmailAlreadyExistsException;
import com.hajji.springbootbasics.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Validation Exception Handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed for one or more fields",
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }



    // 2. Bad or missing request body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleBadRequestException(HttpMessageNotReadableException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Request body is missing or malformed",
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    // User Email Already Exists
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.CONFLICT.value(), // 409
                ex.getMessage(),             // "Email already exists"
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }





    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleRoleAlreadyExistsException(RoleAlreadyExistsException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.CONFLICT.value(), // 409
                ex.getMessage(),             // message from exception
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }


    @ExceptionHandler(PermissionAlreadyExists.class)
    public ResponseEntity<ApiError> handlePermissionAlreadyExists(PermissionAlreadyExists ex) {
        ApiError apiError = new ApiError(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<ApiError> handleInvalidFileTypeException(InvalidFileTypeException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }



    // 3. Generic Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage() != null ? ex.getMessage() : "Unexpected error",
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

}

