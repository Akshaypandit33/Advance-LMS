package com.LMS.Exceptions.ActionsService;

public class ActionsAlreadyExistsException extends RuntimeException {
    public ActionsAlreadyExistsException(String message) {
        super(message);
    }
}
