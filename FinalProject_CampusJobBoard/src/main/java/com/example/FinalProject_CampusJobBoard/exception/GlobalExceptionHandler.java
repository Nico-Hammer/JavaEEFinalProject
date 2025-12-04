package com.example.FinalProject_CampusJobBoard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateApplicationException.class)
    public ResponseEntity<ErrorDetails> duplicateApplicationExceptionHandler(Exception ex){
        ErrorDetails error = new ErrorDetails(0,ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<ErrorDetails> jobNotFoundExceptionHandler(Exception ex){
        ErrorDetails error = new ErrorDetails(0,ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> userNotFoundExceptionHandler(Exception ex){
        ErrorDetails error = new ErrorDetails(0,ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedUser.class)
    public ResponseEntity<ErrorDetails> unauthorizedUserExceptionHandler(Exception ex){
        ErrorDetails error = new ErrorDetails(0,ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }

    // Default case where an internal server error is returned
    @ExceptionHandler(Exception.class)
    public ResponseEntity < ? > globalExceptionHandler(Exception ex) {
        ErrorDetails errorModel = new ErrorDetails(0, ex.getMessage());
        return new ResponseEntity< >(errorModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}