package com.example.FinalProject_CampusJobBoard.service;

import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;
import com.example.FinalProject_CampusJobBoard.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService{
    private final JobRepository repo;
    public JobServiceImpl(JobRepository repo){this.repo = repo;}

    @Override
    public List<Job> findAll() {
        return repo.findAll();
    }

    @Override
    public Job findById(Long Id) {
        return repo.findById(Id).orElse(null);
    }

    @Override
    public Job saveJob(Job job) {
        return repo.save(job);
    }

    @Override
    public void deleteById(Long Id) {
        repo.deleteById(Id);
    }

    @Override
    public List<Job> findByTitleContainingIgnoreCase(String title){
        return repo.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Job> findByStatus(JobStatus status){
        return repo.findByStatus(status);
    }

    @Override
    public List<Job> findByEmployer(User employer){
        return repo.findByEmployer(employer);
    }

    @Override
    public List<Job> findByCategory(String category){
        return repo.findByCategory(category);
    }
}