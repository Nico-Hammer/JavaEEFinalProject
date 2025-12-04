package com.example.FinalProject_CampusJobBoard.exception;

public class UnauthorizedUser extends RuntimeException {
    public UnauthorizedUser(String message) {
        super(message);
    }
}
