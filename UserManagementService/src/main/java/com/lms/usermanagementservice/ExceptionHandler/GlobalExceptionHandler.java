package com.lms.usermanagementservice.ExceptionHandler;


import com.LMS.Exceptions.ActionsService.ActionsAlreadyExistsException;
import com.LMS.Exceptions.ActionsService.ActionsNotFoundException;
import com.LMS.Exceptions.ActionsService.InvalidActionException;
import com.LMS.Exceptions.ErrorResponse;
import com.LMS.Exceptions.InvalidUserInputException;
import com.LMS.Exceptions.PermissionService.PermissionAlreadyExistsException;
import com.LMS.Exceptions.PermissionService.PermissionInUseException;
import com.LMS.Exceptions.PermissionService.PermissionNotFoundException;
import com.LMS.Exceptions.ResourceService.ResourceAlreadyExistsException;
import com.LMS.Exceptions.ResourceService.ResourceNotFoundException;
import com.LMS.Exceptions.RoleService.RoleNotFoundException;
import com.LMS.Exceptions.UnAuthorizedOperationException;
import com.LMS.Exceptions.UserRoleService.UserRoleAlreadyExistsException;
import com.LMS.Exceptions.UserService.EmailAlreadyExistsException;
import com.LMS.Exceptions.UserService.EmailNotFoundException;
import com.LMS.Exceptions.UserService.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return handleAlreadyExists(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return handleNotFound(ex.getMessage());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmailNotFoundException(EmailNotFoundException ex) {
        return handleNotFound(ex.getMessage());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex) {
        return handleNotFound(ex.getMessage());
    }

    @ExceptionHandler(InvalidUserInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUserInput(InvalidUserInputException ex) {
        return handleBadRequest(ex.getMessage());
    }

    @ExceptionHandler(ActionsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleActionsNotFoundException(ActionsNotFoundException ex) {
        return handleNotFound(ex.getMessage());
    }

    @ExceptionHandler(ActionsAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleActionsAlreadyExistsException(ActionsAlreadyExistsException ex) {
        return handleAlreadyExists(ex.getMessage());
    }

    @ExceptionHandler(InvalidActionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidActionException(InvalidActionException ex) {
        return handleBadRequest(ex.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
        return handleAlreadyExists(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return handleNotFound(ex.getMessage());
    }

    @ExceptionHandler(PermissionAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlePermissionAlreadyExistsException(PermissionAlreadyExistsException ex) {
        return handleAlreadyExists(ex.getMessage());
    }

    @ExceptionHandler(PermissionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePermissionNotFoundException(PermissionNotFoundException ex) {
        return handleNotFound(ex.getMessage());
    }
    @ExceptionHandler(PermissionInUseException.class)
    public ResponseEntity<ErrorResponse> handlePermissionInUseException(PermissionInUseException ex) {
        return handleBadRequest(ex.getMessage());
    }

    @ExceptionHandler(UserRoleAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserRoleAlreadyExistsException(UserRoleAlreadyExistsException ex) {
        return handleAlreadyExists(ex.getMessage());
    }

    @ExceptionHandler(UnAuthorizedOperationException.class)
    public ResponseEntity<ErrorResponse> handleUnAuthorizedOperationException(UnAuthorizedOperationException ex) {
        return handleUnAuthorizedOperationException(ex.getMessage());
    }



        @ExceptionHandler(CannotCreateTransactionException.class)
        public ResponseEntity<String> handleCannotCreateTransaction(CannotCreateTransactionException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.SQLGrammarException &&
                    ex.getMessage().contains("does not exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Tenant schema not found.");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error occurred");
        }






    private ResponseEntity<ErrorResponse> handleBadRequest(String message) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorMessage(message)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> handleUnAuthorizedOperationException(String message) {
        ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(message)
                .status(HttpStatus.UNAUTHORIZED)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }



    private ResponseEntity<ErrorResponse> handleNotFound(String message)
    {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .errorMessage(message)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> handleAlreadyExists(String message)
    {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT)
                .errorMessage(message)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
