package com.example.FinalProject_CampusJobBoard.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateApplicationException.class)
    public String handleDuplicateApplicationException(DuplicateApplicationException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "errors/DuplicateApplication";
    }

    @ExceptionHandler(JobNotFoundException.class)
    public String handleJobNotFoundException(JobNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "errors/JobNotFound";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "errors/UserNotFound";
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public String handleUnauthorizedUserException(UnauthorizedUserException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "errors/UnauthorizedUser";
    }

    // Generic exception handler for internal errors
    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
        return "errors/GenericError";
    }
}