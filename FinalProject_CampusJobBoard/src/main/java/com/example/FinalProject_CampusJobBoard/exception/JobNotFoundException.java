package com.example.FinalProject_CampusJobBoard.exception;

import com.example.FinalProject_CampusJobBoard.entity.Job;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(String message){
        super(message);
    }
}
