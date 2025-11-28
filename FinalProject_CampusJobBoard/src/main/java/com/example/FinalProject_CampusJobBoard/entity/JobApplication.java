package com.example.FinalProject_CampusJobBoard.entity;

import com.example.FinalProject_CampusJobBoard.enums.JobApplicationStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long job_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,unique = true)
    private User student;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private JobApplicationStatus status = JobApplicationStatus.SUBMITTED;

    private LocalDateTime appliedAt = LocalDateTime.now();
}
