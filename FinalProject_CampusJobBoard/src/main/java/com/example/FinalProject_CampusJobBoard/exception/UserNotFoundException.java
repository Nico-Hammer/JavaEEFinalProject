package com.example.FinalProject_CampusJobBoard.exception;

import com.example.FinalProject_CampusJobBoard.entity.User;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(User user){
        super();
    }
}