package com.example.FinalProject_CampusJobBoard.controller;

import com.example.FinalProject_CampusJobBoard.Security.user.CustomUserDetailsService;
import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.JobApplication;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobApplicationStatus;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;
import com.example.FinalProject_CampusJobBoard.service.ApplicationService;
import com.example.FinalProject_CampusJobBoard.service.JobService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobService jobService;

    @MockitoBean
    private ApplicationService applicationService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private User testStudent;
    private Job approvedJob;
    private Job pendingJob;
    private Job rejectedJob;
    private JobApplication application;

    @BeforeEach
    void setUp() {
        // Setup test student
        testStudent = new User();
        testStudent.setUser_id(1L);
        testStudent.setFullName("Test Student");
        testStudent.setEmail("student@example.com");
        testStudent.setEnabled(true);

        // Setup approved job
        approvedJob = new Job();
        approvedJob.setJob_id(1L);
        approvedJob.setTitle("Software Developer");
        approvedJob.setDescription("Looking for a software developer");
        approvedJob.setLocation("Campus Building A");
        approvedJob.setSalary(new BigDecimal("50000"));
        approvedJob.setCategory("Technology");
        approvedJob.setDeadline(LocalDate.now().plusDays(30));
        approvedJob.setStatus(JobStatus.APPROVED);
        approvedJob.setCreatedAt(LocalDateTime.now());

        // Setup pending job
        pendingJob = new Job();
        pendingJob.setJob_id(2L);
        pendingJob.setTitle("Pending Job");
        pendingJob.setDescription("This job is pending");
        pendingJob.setStatus(JobStatus.PENDING);

        // Setup rejected job
        rejectedJob = new Job();
        rejectedJob.setJob_id(3L);
        rejectedJob.setTitle("Rejected Job");
        rejectedJob.setDescription("This job was rejected");
        rejectedJob.setStatus(JobStatus.REJECTED);

        // Setup application
        application = new JobApplication();
        application.setJobApplication_id(1L);
        application.setJob(approvedJob);
        application.setStudent(testStudent);
        application.setStatus(JobApplicationStatus.SUBMITTED);
    }

    @AfterEach
    void tearDown() {
        reset(jobService, applicationService, customUserDetailsService);
        testStudent = null;
        approvedJob = null;
        pendingJob = null;
        rejectedJob = null;
        application = null;
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testListApprovedJobs() throws Exception {

    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testListApprovedJobs_EmptyList() throws Exception {

    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testViewJobDetails_Success() throws Exception {

    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testViewJobDetails_NotFound() throws Exception {

    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testViewJobDetails_NotApproved_Pending() throws Exception {

    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testViewJobDetails_NotApproved_Rejected() throws Exception {

    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testApplyToJob_Success() throws Exception {

    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testApplyToJob_JobNotFound() throws Exception {

    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testApplyToJob_NotApproved_Pending() throws Exception {

    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testApplyToJob_NotApproved_Rejected() throws Exception {

    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testViewMyApplications_Success() throws Exception {

    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testViewMyApplications_EmptyList() throws Exception {

    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testViewMyApplications_Unauthorized() throws Exception {

    }
}