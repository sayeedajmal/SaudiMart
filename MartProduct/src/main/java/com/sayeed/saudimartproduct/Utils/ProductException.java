package com.sayeed.saudimartproduct.Utils;

import org.springframework.http.HttpStatus;

public class ProductException extends Exception {
    private HttpStatus status;

    public ProductException() {
        super();
        this.status = HttpStatus.BAD_REQUEST;
    }

    public ProductException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public ProductException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ProductException(String message, Throwable throwable) {
        super(message, throwable);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public ProductException(Throwable throwable) {
        super(throwable);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getStatus() {
        return status;
    }
}