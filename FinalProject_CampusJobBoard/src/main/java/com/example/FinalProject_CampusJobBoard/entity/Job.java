package com.example.FinalProject_CampusJobBoard.entity;

import com.example.FinalProject_CampusJobBoard.enums.JobStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long job_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User employer;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String location;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    @Column(length = 50)
    private String category;

    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private JobStatus status = JobStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();
}
