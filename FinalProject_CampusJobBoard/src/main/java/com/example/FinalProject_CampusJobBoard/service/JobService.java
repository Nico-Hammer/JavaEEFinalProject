package com.example.FinalProject_CampusJobBoard.service;

import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;

import java.util.List;

public interface JobService {
    List<Job> findAll();
    Job findById(Long Id);
    Job saveJob(Job job);
    void deleteById(Long Id);
    List<Job> findByStatus(JobStatus status);
    List<Job> findByEmployer(User employer);
    List<Job> findByTitleContainingIgnoreCase(String title);
    List<Job> findByCategory(String category);
}