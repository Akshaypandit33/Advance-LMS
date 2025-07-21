package com.LMS.Exceptions.PermissionService;

public class PermissionInUseException extends RuntimeException {
    public PermissionInUseException(String message) {
        super(message);
    }
}
