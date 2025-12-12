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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    private JobApplication application2;
    private Job job;
    private Job job2;
    private User student;
    private User student2;
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

        student2 = new User();
        student2.setPassword("Pomepass");
        student2.setEmail("student2@mail.com");
        student2.setFullName("Second");

        job = new Job();
        job.setJob_id(1l);
        job.setCategory("job");
        job.setEmployer(student);
        job.setTitle("Job");
        job.setStatus(jobStatus);

        job2 = new Job();
        job2.setJob_id(2l);
        job2.setCategory("different");
        job2.setEmployer(student2);
        job2.setTitle("Job2");
        job2.setStatus(jobStatus);

        application = new JobApplication();
        application.setJobApplication_id(1l);
        application.setJob(job);
        application.setStudent(student);
        application.setStatus(jobApplicationStatus);

        application2 = new JobApplication();
        application2.setJobApplication_id(2l);
        application2.setJob(job2);
        application2.setStudent(student2);
        application2.setStatus(jobApplicationStatus);
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
        jobRepo.deleteAll();
        userRepo.deleteAll();

        service = null;
        jobService = null;
        userService = null;

        jobStatus = null;
        jobApplicationStatus = null;

        student = null;
        student2 = null;
        job = null;
        job2 = null;
        application = null;
        application2 = null;
    }

    @Test
    void testFindAll() {
        /* create the list of jobs and configure mockito behaviour */
        List<JobApplication> jobApplications = List.of(application,application2);
        when(service.findAll()).thenReturn(jobApplications);
        /* get the result and make sure its what was expected */
        List<JobApplication> foundJobApplications = service.findAll();
        assertThat(foundJobApplications).isNotNull();
        assertThat(foundJobApplications).hasSize(2);
        assertThat(foundJobApplications).contains(application);
        assertThat(foundJobApplications).contains(application2);
        /* make sure the service method was actually called */
        verify(service,times(1)).findAll();
    }

    @Test
    void testFindById() {
        /* configure mockito behaviour */
        when(service.findById(1l)).thenReturn(application);
        /* get the result and make sure its what was expected */
        JobApplication foundJobApplication = service.findById(1l);
        assertThat(foundJobApplication).isNotNull();
        assertThat(foundJobApplication).isEqualTo(application);
        assertThat(foundJobApplication.getJobApplication_id()).isEqualTo(1l);
        /* make sure that the service function was actually called */
        verify(service,times(1)).findById(1l);
    }

    @Test
    void testSave() {
        /* configure mockito behaviour */
        when(service.save(application2)).thenReturn(application2);
        /* get the result and make sure its what was expected */
        JobApplication savedJobApplication = service.save(application2);
        assertThat(savedJobApplication).isNotNull();
        assertThat(savedJobApplication).isEqualTo(application2);
        /* make sure that the service function was actually called */
        verify(service,times(1)).save(application2);
    }

    @Test
    void testDeleteById() {
        /* get the result and make sure its what was expected */
        service.deleteById(2l);
        assertThat(service.findById(2l)).isNull();
        /* make sure that the service function was actually called */
        verify(service,times(1)).deleteById(2l);
    }

    @Test
    void testFindByStudent() {
        /* create the job lists and configure mockito behaviour */
        List<JobApplication> firstStudent = List.of(application);
        List<JobApplication> secondStudent = List.of(application2);
        when(service.findByStudent(student)).thenReturn(firstStudent);
        when(service.findByStudent(student2)).thenReturn(secondStudent);
        /* get the result and make sure its what was expected */
        List<JobApplication> foundFirstStudent = service.findByStudent(student);
        List<JobApplication> foundSecondStudent = service.findByStudent(student2);
        assertThat(foundFirstStudent).isNotNull();
        assertThat(foundFirstStudent).hasSize(1);
        assertThat(foundFirstStudent).contains(application);
        assertThat(foundFirstStudent.get(0).getStudent()).isEqualTo(student);
        assertThat(foundSecondStudent).isNotNull();
        assertThat(foundSecondStudent).hasSize(1);
        assertThat(foundSecondStudent).contains(application2);
        assertThat(foundSecondStudent.get(0).getStudent()).isEqualTo(student2);
        /* make sure that the service function was actually called */
        verify(service,times(1)).findByStudent(student);
        verify(service,times(1)).findByStudent(student2);
    }

    @Test
    void testExistsByJobAndStudent() {
        /* configure mockito behaviour */
        when(service.existsByJobAndStudent(job,student)).thenReturn(true);
        /* get the result and make sure its what was expected */
        boolean exists = service.existsByJobAndStudent(job,student);
        assertTrue(exists);
        /* make sure that the service method actually called */
        verify(service,times(1)).existsByJobAndStudent(job,student);
    }

    @Test
    void testCreateApplication() {
    }

    @Test
    void testFindByStatus() {
        /* create the job lists and configure mockito behaviour */
        List<JobApplication> submittedJobs = List.of(application,application2);
        when(service.findByStatus(jobApplicationStatus)).thenReturn(submittedJobs);
        /* get the result and make sure its what was expected */
        List<JobApplication> foundSubmitted = service.findByStatus(jobApplicationStatus);
        assertThat(foundSubmitted).isNotNull();
        assertThat(foundSubmitted).hasSize(2);
        assertThat(foundSubmitted).contains(application);
        assertThat(foundSubmitted).contains(application2);
        assertThat(foundSubmitted.get(0).getStatus()).isEqualTo(JobApplicationStatus.SUBMITTED);
        assertThat(foundSubmitted.get(1).getStatus()).isEqualTo(JobApplicationStatus.SUBMITTED);
        /* make sure that the service function was actually called */
        verify(service,times(1)).findByStatus(jobApplicationStatus);
    }

    @Test
    void testFindByJob() {
        /* create the job lists and configure mockito behaviour */
        List<JobApplication> firstJob = List.of(application);
        List<JobApplication> secondJob = List.of(application2);
        when(service.findByJob(job)).thenReturn(firstJob);
        when(service.findByJob(job2)).thenReturn(secondJob);
        /* get the result and make sure its what was expected */
        List<JobApplication> foundFirst = service.findByJob(job);
        List<JobApplication> foundSecond = service.findByJob(job2);
        assertThat(foundFirst).isNotNull();
        assertThat(foundFirst).hasSize(1);
        assertThat(foundFirst).contains(application);
        assertThat(foundFirst.get(0).getJob()).isEqualTo(job);
        assertThat(foundSecond).isNotNull();
        assertThat(foundSecond).hasSize(1);
        assertThat(foundSecond).contains(application2);
        assertThat(foundSecond.get(0).getJob()).isEqualTo(job2);
        /* make sure that the service function was actually called */
        verify(service,times(1)).findByJob(job);
        verify(service,times(1)).findByJob(job2);
    }
}