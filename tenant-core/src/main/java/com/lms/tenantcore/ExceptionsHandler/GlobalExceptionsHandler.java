package com.lms.tenantcore.ExceptionsHandler;

import com.LMS.Exceptions.ErrorResponse;
import com.LMS.Exceptions.TenantService.TenantNotFoundExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(TenantNotFoundExceptions.class)
    public ResponseEntity<ErrorResponse> handleTenantNotFoundExceptions(TenantNotFoundExceptions e)
    {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .errorMessage(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
