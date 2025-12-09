package com.example.FinalProject_CampusJobBoard.service;

import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.JobApplication;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobApplicationStatus;
import com.example.FinalProject_CampusJobBoard.repository.JobApplicationRepository;

import java.util.List;

public class ApplicationServiceImpl implements ApplicationService {
    private final JobApplicationRepository repo;

    public ApplicationServiceImpl(JobApplicationRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<JobApplication> findAll() {
        return repo.findAll();
    }

    @Override
    public JobApplication findById(Long Id) {
        return repo.findById(Id).orElse(null);
    }

    @Override
    public JobApplication save(JobApplication jobApplication) {
        return repo.save(jobApplication);
    }

    @Override
    public void deleteById(Long Id) {
        repo.deleteById(Id);
    }

    public List<JobApplication> findByStatus(JobApplicationStatus status) {
        return repo.findByStatus(status);
    }

    public List<JobApplication> findByJob(Job job) {
        return repo.findByJob(job);
    }

    public List<JobApplication> findByStudent(User student) {
        return repo.findByStudent(student);
    }

    boolean existsByJobAndStudent(Job job, User student) {
        return repo.existsByJobAndStudent(job, student);
    }
}