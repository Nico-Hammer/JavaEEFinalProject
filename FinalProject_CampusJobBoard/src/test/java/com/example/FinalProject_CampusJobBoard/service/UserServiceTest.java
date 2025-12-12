package com.example.FinalProject_CampusJobBoard.service;

import com.example.FinalProject_CampusJobBoard.entity.Role;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.repository.JobRepository;
import com.example.FinalProject_CampusJobBoard.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @MockitoBean
    private UserRepository repo;
    @MockitoBean
    private JobRepository jobRepo;

    @Autowired
    private UserService userService;
    @Autowired
    private JobService jobService;

    private User user;
    private Role userRole;
    private Role studentRole;
    private Set<Role> roles;

    @BeforeEach
    void setUp() {
        userRole.setName("USER");
        studentRole.setName("STUDENT");
        roles.add(userRole);
        roles.add(studentRole);
        user = new User();
        user.setUser_id(1L);
        user.setPassword("somePass");
        user.setEmail("John@mail.com");
        user.setFullName("John Test");
        user.setRoles(roles);
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
        user = null;
        userRole = null;
        studentRole = null;
        roles = null;
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findByFullName() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findByRoles_Name() {
    }

    @Test
    void emailExists() {
    }
}