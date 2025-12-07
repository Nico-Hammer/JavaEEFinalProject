package com.example.FinalProject_CampusJobBoard.service;

import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;

import java.util.List;

public interface JobService {
    List<Job> findAll();
    List<Job> findByStatus(JobStatus status);
    List<Job> findByEmployer(User employer);
    List<Job> findByCategory(String category);
    Job findById(Long Id);
    Job saveJob(Job job);
    void deleteById(Long Id);
}