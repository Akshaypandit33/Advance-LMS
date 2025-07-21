package com.LMS.Exceptions.UserRoleService;

public class UserRoleAlreadyExistsException extends RuntimeException {
    public UserRoleAlreadyExistsException(String message) {
        super(message);
    }
}
