package com.example.FinalProject_CampusJobBoard.repository;

import com.example.FinalProject_CampusJobBoard.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JobApplicationRepositoryTest {

    @Mock
    private JobApplicationRepository repo;
    @Mock
    private JobRepository jobRepo;
    @Mock
    private UserRepository userRepo;

    @Test
    void findByStatus() {
    }

    @Test
    void findByJob() {
    }

    @Test
    void findByStudent() {
    }

    @Test
    void existsByJobAndStudent() {
    }
}