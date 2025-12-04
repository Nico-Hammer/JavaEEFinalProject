package com.example.FinalProject_CampusJobBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;
import com.example.FinalProject_CampusJobBoard.entity.User;
import java.util.List;

public interface JobRepository extends JpaRepository<Job,Long> {
	List<Job> findByStatus(JobStatus status);
	List<Job> findByEmployer(User employer);
	List<Job> findByCategory(String category);
	List<Job> findByTitleContainingIgnoreCase(String title);
}
