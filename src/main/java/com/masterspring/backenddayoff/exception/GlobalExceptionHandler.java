package com.masterspring.backenddayoff.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> catchException(Exception e) {
        var errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(500);
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> catchException(AppException e) {
        var errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(e.getStatusCode());
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        var errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(400);
        errorResponse.setMessage(extractValidationMessage(ex));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    private String extractValidationMessage(MethodArgumentNotValidException exception) {
        String exceptionMessage = exception.getMessage();
        String[] messageParts = exceptionMessage.split(";");
        String finalPart = messageParts[messageParts.length - 1];
        return finalPart.trim().replaceAll("default message \\[|]]", "");
    }
}