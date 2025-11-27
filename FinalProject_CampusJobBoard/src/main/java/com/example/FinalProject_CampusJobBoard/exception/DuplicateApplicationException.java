package com.example.FinalProject_CampusJobBoard.exception;

import com.example.FinalProject_CampusJobBoard.entity.JobApplication;

public class DuplicateApplicationException extends RuntimeException {
    public DuplicateApplicationException(JobApplication jobApplication){
        super("Duplicate job application with id " + jobApplication.getId());
    }
}
