package com.LMS.Exceptions;

public class UnAuthorizedOperationException extends RuntimeException {
    public UnAuthorizedOperationException(String message) {
        super(message);
    }
}
