package com.example.FinalProject_CampusJobBoard.exception;

import com.example.FinalProject_CampusJobBoard.entity.Job;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(Job job){
        super("Job with " + job.getTitle() + " not found.");
    }
    public JobNotFoundException(Job job){
        super("Job with id " + job.getId() + " not found");
    }
}
