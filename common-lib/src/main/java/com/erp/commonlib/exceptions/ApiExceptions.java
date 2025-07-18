package com.erp.commonlib.exceptions;

public class ApiExceptions extends RuntimeException {

    private final int status;

    public ApiExceptions(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
