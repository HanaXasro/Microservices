package com.erp.commonlib.exceptions;

public class BadRequestException extends ApiExceptions {
    public BadRequestException(String message) {
        super(message, 400);
    }
}
