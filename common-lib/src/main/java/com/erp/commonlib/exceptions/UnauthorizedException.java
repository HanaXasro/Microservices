package com.erp.commonlib.exceptions;

public class UnauthorizedException extends ApiExceptions {

    public UnauthorizedException(String message)
    {
        super(message,401);
    }
}
