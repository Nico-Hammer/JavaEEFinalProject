package com.example.FinalProject_CampusJobBoard.repository;

import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class JobRepositoryTest {

    @Mock
    private JobRepository repo;
    @Mock
    private UserRepository userRepo;

    Job job;
    User employer;
    JobStatus status;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        employer = new User();
        employer.setFullName("john employer");
        employer.setEmail("e@mail.com");
        employer.setPassword("somePass");
        status = JobStatus.PENDING;
        job = new Job();
        job.setJob_id(1l);
        job.setStatus(status);
        job.setCategory("test category");
        job.setEmployer(employer);
        job.setTitle("title");
    }

    @AfterEach
    void teardown(){
        repo.deleteAll();
        userRepo.deleteAll();

        job = null;
        employer = null;
        status = null;
    }

    @Test
    void findByTitleContainingIgnoreCase() {
        /* create the jobs list set up the mock repository and return results */
        List<Job> jobs = Arrays.asList(job);
        when(repo.findByTitleContainingIgnoreCase("title")).thenReturn(jobs);
        when(repo.findByTitleContainingIgnoreCase("Title")).thenReturn(jobs);
        when(repo.findByTitleContainingIgnoreCase("TITLE")).thenReturn(jobs);
        /* get the job by title ignoring case and make sure its the expected result */
        List<Job> foundJob = repo.findByTitleContainingIgnoreCase("title");
        List<Job> foundJobCapital = repo.findByTitleContainingIgnoreCase(("Title"));
        List<Job> foundJobTitleCase = repo.findByTitleContainingIgnoreCase("TITLE");
        assertThat(foundJob).hasSize(1);
        assertThat(foundJob.get(0).getTitle()).isEqualTo(job.getTitle());
        assertThat(foundJobCapital).hasSize(1);
        assertThat(foundJobCapital.get(0).getTitle()).isEqualTo(job.getTitle());
        assertThat(foundJobTitleCase).hasSize(1);
        assertThat(foundJobTitleCase.get(0).getTitle()).isEqualTo(job.getTitle());
        /* make sure that the repository method was actually called */
        verify(repo,times(1)).findByTitleContainingIgnoreCase("Title");
    }

    @Test
    void findByStatus() {
        /* create the jobs list set up the mock repository and return results */
        List<Job> jobs = Arrays.asList(job);
        when(repo.findByStatus(JobStatus.PENDING)).thenReturn(jobs);
        /* get the job by status and make sure its the expected result */
        List<Job> foundJob = repo.findByStatus(JobStatus.PENDING);
        assertThat(foundJob).hasSize(1);
        assertThat(foundJob.get(0).getStatus()).isEqualTo(status);
        /* make sure the repository method was actually called */
        verify(repo,times(1)).findByStatus(JobStatus.PENDING);
    }

    @Test
    void findByEmployer() {
        /* create the jobs list set up the mock repository and return results */
        List<Job> jobs = Arrays.asList(job);
        when(repo.findByEmployer(employer)).thenReturn(jobs);
        /* get the job by employer and make sure its the expected result */
        List<Job> foundJob = repo.findByEmployer(employer);
        assertThat(foundJob).hasSize(1);
        assertThat(foundJob.get(0).getEmployer()).isEqualTo(employer);
        /* make sure the repository method was actually called */
        verify(repo,times(1)).findByEmployer(employer);
    }

    @Test
    void findByCategory() {
    }
}