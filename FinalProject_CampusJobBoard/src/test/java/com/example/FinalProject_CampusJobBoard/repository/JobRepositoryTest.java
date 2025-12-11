package com.example.FinalProject_CampusJobBoard.repository;

import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JobRepositoryTest {

    @Mock
    private JobRepository repo;
    @Mock
    private UserRepository userRepo;

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
    }

    @AfterEach
    void teardown(){
        repo.deleteAll();
        userRepo.deleteAll();

        employer = null;
        status = null;
    }

    @Test
    void findByTitleContainingIgnoreCase() {
    }

    @Test
    void findByStatus() {
    }

    @Test
    void findByEmployer() {
    }

    @Test
    void findByCategory() {
    }
}