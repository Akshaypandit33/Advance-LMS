package com.LMS.Exceptions.ActionsService;

public class ActionsNotFoundException extends RuntimeException {
    public ActionsNotFoundException(String message) {
        super(message);
    }
}
