package com.example.FinalProject_CampusJobBoard.repository;

import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.JobApplication;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByStatus(JobApplicationStatus status);
    List<JobApplication> findByJob(Job job);
    List<JobApplication> findByStudent(User student);
    boolean existsByJobAndStudent(Job job, User student);
}