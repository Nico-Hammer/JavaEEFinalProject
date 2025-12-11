package com.example.FinalProject_CampusJobBoard.repository;

import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.JobApplication;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobApplicationStatus;
import org.aspectj.lang.annotation.Before;
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
class JobApplicationRepositoryTest {

    @Mock
    private JobApplicationRepository repo;
    @Mock
    private JobRepository jobRepo;
    @Mock
    private UserRepository userRepo;

    private Job job;
    private User student;
    private JobApplication application;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        job = new Job();
        job.setJob_id(1l);
        student = new User();

        student.setPassword("somePass");
        student.setEmail("e@mail.com");
        student.setFullName("test");

        application = new JobApplication();
        application.setJob(job);
        application.setStudent(student);
        application.setJobApplication_id(1l);
        application.setStatus(JobApplicationStatus.SUBMITTED);
    }

    @AfterEach
    void teardown(){
        repo.deleteAll();
        jobRepo.deleteAll();
        userRepo.deleteAll();

        job = null;
        student = null;
        application = null;
    }

    @Test
    void testFindByStatus() {
        /* create the jobapplication list and setup mock repo and return result */
        List<JobApplication> applications = Arrays.asList(application);
        when(repo.findByStatus(JobApplicationStatus.SUBMITTED)).thenReturn(applications);
        /* get the job application by status and make sure that it has the expected status */
        List<JobApplication> foundApplications = repo.findByStatus(JobApplicationStatus.SUBMITTED);
        assertThat(foundApplications).hasSize(1);
        assertThat(foundApplications.getFirst().getStatus()).isEqualTo(JobApplicationStatus.SUBMITTED);
        /* make sure that the repository function is actually called */
        verify(repo,times(1)).findByStatus(JobApplicationStatus.SUBMITTED);
    }

    @Test
    void testFindByJob() {
        /* create the jobapplication list and setup mock repo and return result */
        List<JobApplication> applications = Arrays.asList(application);
        when(repo.findByJob(job)).thenReturn(applications);
        /* get the job application by job and make sure that it has the expected job application id */
        List<JobApplication> foundApplications = repo.findByJob(job);
        assertThat(foundApplications).hasSize(1);
        assertThat(foundApplications.getFirst().getJobApplication_id()).isEqualTo(application.getJobApplication_id());
        /* make sure that the repository function is actually called */
        verify(repo,times(1)).findByJob(job);
    }

    @Test
    void testFindByStudent() {
        /* create the jobapplication list and setup mock repo and return result */
        List<JobApplication> applications = Arrays.asList(application);
        when(repo.findByStudent(student)).thenReturn(applications);
        /* get the job application by job and make sure that it has the expected student */
        List<JobApplication> foundApplications = repo.findByStudent(student);
        assertThat(foundApplications).hasSize(1);
        assertThat(foundApplications.getFirst().getStudent()).isEqualTo(student);
        /* make sure that the repository function is actually called */
        verify(repo,times(1)).findByStudent(student);
    }

    @Test
    void testExistsByJobAndStudent() {
    }
}