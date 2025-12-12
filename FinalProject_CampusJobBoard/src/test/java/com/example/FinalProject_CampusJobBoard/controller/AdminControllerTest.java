package com.example.FinalProject_CampusJobBoard.controller;

import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;
import com.example.FinalProject_CampusJobBoard.service.JobService;
import com.example.FinalProject_CampusJobBoard.service.UserService;
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

@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobService jobService;

    @MockitoBean
    private UserService userService;

    private User testUser;
    private User disabledUser;
    private Job pendingJob;
    private Job approvedJob;

    @BeforeEach
    void setUp() {
        // Setup test user
        testUser = new User();
        testUser.setUser_id(1L);
        testUser.setFullName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setEnabled(true);

        // Setup disabled user
        disabledUser = new User();
        disabledUser.setUser_id(2L);
        disabledUser.setFullName("Disabled User");
        disabledUser.setEmail("disabled@example.com");
        disabledUser.setEnabled(false);

        // Setup pending job
        pendingJob = new Job();
        pendingJob.setJob_id(1L);
        pendingJob.setTitle("Pending Job");
        pendingJob.setDescription("This job is pending approval");
        pendingJob.setLocation("Campus Building A");
        pendingJob.setSalary(new BigDecimal("50000"));
        pendingJob.setCategory("Technology");
        pendingJob.setDeadline(LocalDate.now().plusDays(30));
        pendingJob.setStatus(JobStatus.PENDING);
        pendingJob.setCreatedAt(LocalDateTime.now());

        // Setup approved job (for edge case testing)
        approvedJob = new Job();
        approvedJob.setJob_id(2L);
        approvedJob.setTitle("Approved Job");
        approvedJob.setDescription("This job is already approved");
        approvedJob.setStatus(JobStatus.APPROVED);
    }

    @AfterEach
    void tearDown() {
        reset(jobService, userService);
        testUser = null;
        disabledUser = null;
        pendingJob = null;
        approvedJob = null;
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testListAllUsers() throws Exception {
        List<User> users = List.of(testUser, disabledUser);
        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", users));

        verify(userService, times(1)).findAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testListAllUsers_EmptyList() throws Exception {
        when(userService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", Collections.emptyList()));

        verify(userService, times(1)).findAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testListAllPendingJobs() throws Exception {
        List<Job> pendingJobs = Collections.singletonList(pendingJob);
        when(jobService.findByStatus(JobStatus.PENDING)).thenReturn(pendingJobs);

        mockMvc.perform(get("/admin/pending-jobs"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pending-jobs"))
                .andExpect(model().attributeExists("pendingJobs"))
                .andExpect(model().attribute("pendingJobs", pendingJobs));

        verify(jobService, times(1)).findByStatus(JobStatus.PENDING);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testListAllPendingJobs_EmptyList() throws Exception {

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAcceptJobPosting_Success() throws Exception {

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAcceptJobPosting_AlreadyApproved() throws Exception {

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAcceptJobPosting_NotFound() throws Exception {

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeclineJobPosting_Success() throws Exception {

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeclineJobPosting_NotFound() throws Exception {

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testActivateUser_Success() throws Exception {

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testActivateUser_AlreadyEnabled() throws Exception {

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testActivateUser_NotFound() throws Exception {

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeactivateUser_Success() throws Exception {

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeactivateUser_AlreadyDisabled() throws Exception {

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeactivateUser_NotFound() throws Exception {

    }
}