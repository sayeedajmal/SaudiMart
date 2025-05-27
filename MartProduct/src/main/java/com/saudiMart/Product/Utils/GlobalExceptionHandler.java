package com.saudiMart.Product.Utils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        return buildErrorResponse(exception.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Handles {@link ProductException} thrown by the application.
     *
     * This exception handler is responsible for catching {@link ProductException}s
     * thrown by the application, and returning a 409 CONFLICT response with
     **/
    @ExceptionHandler(ProductException.class)
    public ResponseEntity<?> handleUserException(ProductException exception) {
        return buildErrorResponse(exception.getMessage(), HttpStatus.CONFLICT);
    }

    private ResponseEntity<?> buildErrorResponse(String message, HttpStatus status) {
        UserExcResponse response = new UserExcResponse();
        response.setMessage(message);
        response.setStatus(status.value());
        response.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, status);
    }
}
