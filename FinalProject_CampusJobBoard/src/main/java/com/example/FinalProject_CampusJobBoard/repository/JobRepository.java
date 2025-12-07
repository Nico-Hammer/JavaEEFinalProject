package com.example.FinalProject_CampusJobBoard.repository;

import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FinalProject_CampusJobBoard.entity.Job;
import java.util.List;

public interface JobRepository extends JpaRepository<Job,Long> {
	List<Job> findByTitleContainingIgnoreCase(String title);
    List<Job> findByStatus(JobStatus status);
    List<Job> findByEmployer(User employer);
    List<Job> findByCategory(String category);
}