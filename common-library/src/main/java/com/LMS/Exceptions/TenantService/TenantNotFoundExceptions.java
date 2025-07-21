package com.LMS.Exceptions.TenantService;

public class TenantNotFoundExceptions extends RuntimeException {
    public TenantNotFoundExceptions(String message) {
        super(message);
    }
}
