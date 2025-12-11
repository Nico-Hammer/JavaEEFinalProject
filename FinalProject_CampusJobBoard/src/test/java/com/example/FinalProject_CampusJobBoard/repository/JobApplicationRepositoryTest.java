package com.example.FinalProject_CampusJobBoard.repository;

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