package com.example.FinalProject_CampusJobBoard.exception;

import com.example.FinalProject_CampusJobBoard.entity.User;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(User user){
        super("User " + user.getName() + " not found.");
    }

    public UserNotFoundException(User user){
        super("User with id " + user.getId() + " not found");
    }
}
