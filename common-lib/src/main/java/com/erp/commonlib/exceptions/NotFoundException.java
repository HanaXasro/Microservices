package com.erp.commonlib.exceptions;

public class NotFoundException extends ApiExceptions {
    public NotFoundException(String message) {
        super(message, 404);
    }
}
