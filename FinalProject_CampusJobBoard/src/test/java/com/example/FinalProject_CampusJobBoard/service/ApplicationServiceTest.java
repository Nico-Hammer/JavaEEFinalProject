package com.example.FinalProject_CampusJobBoard.service;

import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.JobApplication;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobApplicationStatus;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;
import com.example.FinalProject_CampusJobBoard.repository.JobApplicationRepository;
import com.example.FinalProject_CampusJobBoard.repository.JobRepository;
import com.example.FinalProject_CampusJobBoard.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationServiceTest {

    @MockitoBean
    private JobApplicationRepository repo;
    @MockitoBean
    private JobRepository jobRepo;
    @MockitoBean
    private UserRepository userRepo;

    @MockitoBean
    private ApplicationService service;
    @MockitoBean
    private JobService jobService;
    @MockitoBean
    private UserService userService;

    private JobApplication application;
    private Job job;
    private User student;
    private JobStatus jobStatus;
    private JobApplicationStatus jobApplicationStatus;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        jobStatus = JobStatus.PENDING;
        jobApplicationStatus = JobApplicationStatus.SUBMITTED;

        student = new User();
        student.setPassword("somePass");
        student.setEmail("student@mail.com");
        student.setFullName("Student");

        job = new Job();
        job.setJob_id(1l);
        job.setCategory("job");
        job.setEmployer(student);
        job.setTitle("Job");
        job.setStatus(jobStatus);

        application = new JobApplication();
        application.setJobApplication_id(1l);
        application.setJob(job);
        application.setStudent(student);
        application.setStatus(jobApplicationStatus);
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
        jobRepo.deleteAll();
        userRepo.deleteAll();

        service = null;
        jobService = null;
        userService = null;
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findByStudent() {
    }

    @Test
    void existsByJobAndStudent() {
    }

    @Test
    void createApplication() {
    }

    @Test
    void findByStatus() {
    }

    @Test
    void findByJob() {
    }
}