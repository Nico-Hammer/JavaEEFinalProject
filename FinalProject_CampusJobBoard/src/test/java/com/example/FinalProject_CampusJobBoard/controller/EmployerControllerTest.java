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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;



@WebMvcTest(controllers = EmployerController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobService jobService;

    @MockitoBean
    private ApplicationService applicationService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private User testEmployer;
    private User otherEmployer;
    private Job employerJob;
    private Job otherEmployerJob;
    private Job rejectedJob;
    private JobApplication application;

    @BeforeEach
    void setUp() {
        // Setup test employer
        testEmployer = new User();
        testEmployer.setUser_id(1L);
        testEmployer.setFullName("Test Employer");
        testEmployer.setEmail("employer@example.com");
        testEmployer.setEnabled(true);

        // Setup other employer
        otherEmployer = new User();
        otherEmployer.setUser_id(2L);
        otherEmployer.setFullName("Other Employer");
        otherEmployer.setEmail("other@example.com");

        // Setup employer's job
        employerJob = new Job();
        employerJob.setJob_id(1L);
        employerJob.setTitle("My Job");
        employerJob.setDescription("Job description");
        employerJob.setLocation("Location");
        employerJob.setSalary(new BigDecimal("50000"));
        employerJob.setCategory("Tech");
        employerJob.setDeadline(LocalDate.now().plusDays(30));
        employerJob.setStatus(JobStatus.PENDING);
        employerJob.setEmployer(testEmployer);
        employerJob.setCreatedAt(LocalDateTime.now());

        // Setup other employer's job
        otherEmployerJob = new Job();
        otherEmployerJob.setJob_id(2L);
        otherEmployerJob.setTitle("Other Job");
        otherEmployerJob.setDescription("Other description");
        otherEmployerJob.setEmployer(otherEmployer);
        otherEmployerJob.setStatus(JobStatus.APPROVED);

        // Setup rejected job
        rejectedJob = new Job();
        rejectedJob.setJob_id(3L);
        rejectedJob.setTitle("Rejected Job");
        rejectedJob.setDescription("Rejected description");
        rejectedJob.setEmployer(testEmployer);
        rejectedJob.setStatus(JobStatus.REJECTED);

        // Setup application
        application = new JobApplication();
        application.setJobApplication_id(1L);
        application.setJob(employerJob);
        application.setStatus(JobApplicationStatus.SUBMITTED);
    }

    @AfterEach
    void tearDown() {
        reset(jobService, applicationService, customUserDetailsService);
        testEmployer = null;
        otherEmployer = null;
        employerJob = null;
        otherEmployerJob = null;
        rejectedJob = null;
        application = null;
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testListMyJobs() throws Exception {
        List<Job> jobs = Collections.singletonList(employerJob);
        when(customUserDetailsService.getCurrentUser()).thenReturn(testEmployer);
        when(jobService.findByEmployer(testEmployer)).thenReturn(jobs);

        mockMvc.perform(get("/employer/myjobs"))
                .andExpect(status().isOk())
                .andExpect(view().name("employer/my-jobs"))
                .andExpect(model().attributeExists("jobs"))
                .andExpect(model().attribute("jobs", jobs));

        verify(customUserDetailsService, times(1)).getCurrentUser();
        verify(jobService, times(1)).findByEmployer(testEmployer);
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testListMyJobs_EmptyList() throws Exception {
        when(customUserDetailsService.getCurrentUser()).thenReturn(testEmployer);
        when(jobService.findByEmployer(testEmployer)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/employer/myjobs"))
                .andExpect(status().isOk())
                .andExpect(view().name("employer/my-jobs"))
                .andExpect(model().attributeExists("jobs"))
                .andExpect(model().attribute("jobs", Collections.emptyList()));

        verify(jobService, times(1)).findByEmployer(testEmployer);
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testShowCreateJobForm() throws Exception {
        mockMvc.perform(get("/employer/jobs/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("employer/job-form"))
                .andExpect(model().attributeExists("job"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testCreateJob() throws Exception {
        when(customUserDetailsService.getCurrentUser()).thenReturn(testEmployer);
        when(jobService.saveJob(any(Job.class))).thenReturn(employerJob);

        mockMvc.perform(post("/employer/jobs")
                        .with(csrf())
                        .param("title", "New Job")
                        .param("description", "Description")
                        .param("location", "Location")
                        .param("salary", "50000")
                        .param("category", "Tech")
                        .param("deadline", "2025-12-31"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employer/myjobs"));

        verify(customUserDetailsService, times(1)).getCurrentUser();
        verify(jobService, times(1)).saveJob(any(Job.class));
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testShowEditJobForm_Success() throws Exception {
        when(customUserDetailsService.getCurrentUser()).thenReturn(testEmployer);
        when(jobService.findById(1L)).thenReturn(employerJob);

        mockMvc.perform(get("/employer/jobs/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("employer/job-form"))
                .andExpect(model().attributeExists("job"))
                .andExpect(model().attribute("job", employerJob));

        verify(customUserDetailsService, times(1)).getCurrentUser();
        verify(jobService, times(1)).findById(1L);
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testShowEditJobForm_NotFound() throws Exception {
        when(customUserDetailsService.getCurrentUser()).thenReturn(testEmployer);
        when(jobService.findById(999L)).thenReturn(null);

        mockMvc.perform(get("/employer/jobs/999/edit"))
                .andExpect(status().isNotFound());

        verify(jobService, times(1)).findById(999L);
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testShowEditJobForm_Unauthorized() throws Exception {
        when(customUserDetailsService.getCurrentUser()).thenReturn(testEmployer);
        when(jobService.findById(2L)).thenReturn(otherEmployerJob);

        mockMvc.perform(get("/employer/jobs/2/edit"))
                .andExpect(status().isUnauthorized());

        verify(jobService, times(1)).findById(2L);
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testUpdateJob_Success() throws Exception {
        when(customUserDetailsService.getCurrentUser()).thenReturn(testEmployer);
        when(jobService.findById(1L)).thenReturn(employerJob);
        when(jobService.saveJob(any(Job.class))).thenReturn(employerJob);

        mockMvc.perform(post("/employer/jobs/1/edit")
                        .with(csrf())
                        .param("title", "Updated Title")
                        .param("description", "Updated Description")
                        .param("location", "New Location")
                        .param("salary", "60000")
                        .param("category", "New Category")
                        .param("deadline", "2025-12-31"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employer/myjobs"));

        verify(jobService, times(1)).findById(1L);
        verify(jobService, times(1)).saveJob(any(Job.class));
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testUpdateJob_RejectedToPending() throws Exception {
        when(customUserDetailsService.getCurrentUser()).thenReturn(testEmployer);
        when(jobService.findById(3L)).thenReturn(rejectedJob);
        when(jobService.saveJob(any(Job.class))).thenReturn(rejectedJob);

        mockMvc.perform(post("/employer/jobs/3/edit")
                        .with(csrf())
                        .param("title", "Updated Rejected Job")
                        .param("description", "Updated Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employer/myjobs"));

        verify(jobService, times(1)).findById(3L);
        verify(jobService, times(1)).saveJob(any(Job.class));
        // Verify status changed from REJECTED to PENDING
        assert rejectedJob.getStatus() == JobStatus.PENDING;
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testUpdateJob_NotFound() throws Exception {

    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testUpdateJob_Unauthorized() throws Exception {

    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testDeleteJob_Success() throws Exception {

    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testDeleteJob_NotFound() throws Exception {

    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testDeleteJob_Unauthorized() throws Exception {

    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testViewAllApplications() throws Exception {

    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testViewAllApplications_EmptyList() throws Exception {

    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testViewAllApplications_MultipleJobs() throws Exception {

    }
}