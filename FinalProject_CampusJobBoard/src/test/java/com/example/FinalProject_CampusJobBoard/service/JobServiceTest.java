package com.example.FinalProject_CampusJobBoard.service;

import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JobServiceTest {

    @MockitoBean
    private JobRepository repo;
    @MockitoBean
    private JobService service;
    @MockitoBean
    private UserRepository userRepo;
    @MockitoBean
    private UserService userService;

    private Job job;
    private Job job2;
    private User user;
    private JobStatus status;
    private User user2;
    private JobStatus status2;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        status = JobStatus.APPROVED;
        status2 = JobStatus.REJECTED;

        job = new Job();
        job.setJob_id(1l);
        job.setTitle("Job");
        job.setEmployer(user);
        job.setCategory("Labour");
        job.setStatus(status);
        job2 = new Job();
        job2.setJob_id(2l);
        job2.setTitle("Job2");
        job2.setEmployer(user2);
        job2.setCategory("Marketing");
        job2.setStatus(status2);

        user = new User();
        user.setEmail("e@mail.com");
        user.setFullName("employer");
        user.setPassword("somePass");
        user2 = new User();
        user2.setEmail("e@mail.ca");
        user2.setFullName("employer2");
        user2.setPassword("Somepass");
    }

    @AfterEach
    void teardown(){
        repo.deleteAll();
        userRepo.deleteAll();

        service = null;
        userService = null;

        status = null;
        status2 = null;

        job = null;
        job2 = null;
        user = null;
        user2 = null;
    }

    @Test
    void testFindAll() {
        /* create the list of jobs and configure mockito behaviour */
        List<Job> jobs = List.of(job,job2);
        when(service.findAll()).thenReturn(jobs);
        /* get the result and make sure its what was expected */
        List<Job> foundJobs = service.findAll();
        assertThat(foundJobs).isNotNull();
        assertThat(foundJobs).hasSize(2);
        assertThat(foundJobs).contains(job);
        assertThat(foundJobs).contains(job2);
        /* make sure the service method was actually called */
        verify(service,times(1)).findAll();
    }

    @Test
    void testFindById() {
    }

    @Test
    void testFaveJob() {
    }

    @Test
    void testDeleteById() {
    }

    @Test
    void testFindByStatus() {
    }

    @Test
    void testFindByEmployer() {
    }

    @Test
    void testFindByTitleContainingIgnoreCase() {
    }

    @Test
    void testFindByCategory() {
    }
}