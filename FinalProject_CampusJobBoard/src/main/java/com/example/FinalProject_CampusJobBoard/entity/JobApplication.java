package com.example.FinalProject_CampusJobBoard.entity;

import com.example.FinalProject_CampusJobBoard.enums.JobApplicationStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobApplication_id;

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

    public Long getJobApplication_id() {
        return jobApplication_id;
    }

    public void setJobApplication_id(Long jobApplication_id) {
        this.jobApplication_id = jobApplication_id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public JobApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(JobApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
}
