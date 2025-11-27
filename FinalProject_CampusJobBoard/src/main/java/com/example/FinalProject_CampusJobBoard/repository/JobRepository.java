package com.example.FinalProject_CampusJobBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FinalProject_CampusJobBoard.entity.Job;


public interface JobRepository extends JpaRepository<Job,Long> {
}
