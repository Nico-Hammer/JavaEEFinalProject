package com.example.FinalProject_CampusJobBoard.service;

import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.JobApplication;
import com.example.FinalProject_CampusJobBoard.entity.User;

import java.util.List;

public interface ApplicationService {
    List<JobApplication> findByJob(Job job);
    List<JobApplication> findByStudent(User student);
    boolean existsByJobAndStudent(Job job, User student);
    JobApplication save(JobApplication jobApplication);
    void deleteById(Long Id);
}