package com.example.FinalProject_CampusJobBoard.repository;

import com.example.FinalProject_CampusJobBoard.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.User;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {
	List<JobApplication> findByJob(Job job);
	List<JobApplication> findByStudent(User student);
	boolean existsByJobAndStudent(Job job, User student);
}
