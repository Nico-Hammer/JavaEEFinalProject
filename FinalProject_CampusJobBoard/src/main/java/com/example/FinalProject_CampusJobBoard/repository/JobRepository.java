package com.example.FinalProject_CampusJobBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FinalProject_CampusJobBoard.entity.Job;
import java.util.List;

public interface JobRepository extends JpaRepository<Job,Long> {
	List<Job> findByTitleContainingIgnoreCase(String title);
}