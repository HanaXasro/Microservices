package com.erp.commonlib.exceptions;

public class DuplicatePhoneException extends ApiExceptions {
    public DuplicatePhoneException(String message) {
        super(message, 422);
    }
}
