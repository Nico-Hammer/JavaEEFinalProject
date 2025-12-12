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
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    private User user2;
    private Role userRole;
    private Role studentRole;
    private Role employerRole;
    private Set<Role> studentRoles;
    private Set<Role> employerRoles;

    @BeforeEach
    void setUp() {
        userRole = new Role();
        studentRole = new Role();
        employerRole = new Role();

        userRole.setName("USER");
        studentRole.setName("STUDENT");
        employerRole.setName("EMPLOYER");

        studentRoles = new HashSet<>();
        employerRoles = new HashSet<>();

        studentRoles.add(userRole);
        studentRoles.add(studentRole);
        employerRoles.add(userRole);
        employerRoles.add(employerRole);

        user = new User();
        user.setUser_id(1L);
        user.setPassword("somePass");
        user.setEmail("John@mail.com");
        user.setFullName("John Test");
        user.setRoles(studentRoles);

        user2 = new User();
        user2.setUser_id(2L);
        user2.setPassword("Somepass");
        user2.setEmail("alice@mail.com");
        user2.setFullName("Alice test");
        user2.setRoles(employerRoles);
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
        jobRepo.deleteAll();

        user = null;
        user2 = null;

        userRole = null;
        studentRole = null;
        employerRole = null;

        studentRoles = null;
        employerRoles = null;
    }

    @Test
    void testFindAll() {
        /* create the list of users and set up the mockito behaviour */
        List<User> users = List.of(user, user2);
        when(repo.findAll()).thenReturn(users);
        /* get the result and make sure its what was expected */
        List<User> result = userService.findAll();
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).contains(user);
        assertThat(result).contains(user2);
        /* make sure the repository method was actually called */
        verify(repo, times(1)).findAll();
    }

    @Test
    void testFindById() {
    }

    @Test
    void testSave() {
    }

    @Test
    void testDeleteById() {
    }

    @Test
    void testFindByFullName() {
        /* configure mockito behaviour */
        when(repo.findByFullName("John Test")).thenReturn(Optional.of(user));
        /* get the result and make sure its what was expected */
        Optional<User> foundUser = repo.findByFullName("John Test");
        assertThat(foundUser).isPresent();
        assertThat(foundUser).contains(user);
        /* make sure that the repository function was actually called */
        verify(repo,times(1)).findByFullName("John Test");
    }

    @Test
    void testFindByEmail() {
        /* configure mockito behaviour */
        when(repo.findByEmail("alice@mail.com")).thenReturn(Optional.of(user2));
        /* get the result and make sure its what was expected */
        Optional<User> foundUser = repo.findByEmail("alice@mail.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser).contains(user2);
        /* make sure that the repository function was actually called */
        verify(repo,times(1)).findByEmail("alice@mail.com");
    }

    @Test
    void testFindByRoles_Name() {
    }

    @Test
    void testEmailExists() {
        /* configure mockito behaviour */
        when(repo.findByEmail("john@mail.com")).thenReturn(Optional.of(user));
        /* get the result and make sure its what was expected */
        boolean exists = userService.emailExists("john@mail.com");
        assertTrue(exists);
        /* verify that the method was actually called */
        verify(repo, times(1)).findByEmail("john@mail.com");
    }
}