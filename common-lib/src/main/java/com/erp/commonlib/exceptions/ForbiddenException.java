package com.erp.commonlib.exceptions;

public class ForbiddenException extends ApiExceptions {

    public ForbiddenException(String message)
    {
        super(message,403);
    }
}
