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
    private UserService userService;

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
        when(userService.findAll()).thenReturn(users);
        /* get the result and make sure its what was expected */
        List<User> result = userService.findAll();
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).contains(user);
        assertThat(result).contains(user2);
        /* make sure the repository method was actually called */
        verify(userService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        /* configure mockito behaviour */
        when(userService.findById(2l)).thenReturn(user2);
        /* get the result and make sure its what was expected */
        Optional<User> foundUser = Optional.ofNullable(userService.findById(2l));
        assertThat(foundUser).isPresent();
        assertThat(foundUser).contains(user2);
        /* make sure that the repository function was actually called */
        verify(userService,times(1)).findById(2l);
    }

    @Test
    void testSave() {
        /* configure mockito behaviour */
        when(userService.save(user)).thenReturn(user);
        /* get the result and make sure its what was expected */
        User savedUser = userService.save(user);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser).isEqualTo(user);
        /* make sure that the repository function was actually called */
        verify(userService,times(1)).save(user);
    }

    @Test
    void testDeleteById() {
        /* get the result and make sure its what was expected */
        userService.deleteById(1l);
        assertThat(userService.findById(1l)).isNull();
        /* make sure that the repository function was actually called */
        verify(userService,times(1)).deleteById(1l);
    }

    @Test
    void testFindByFullName() {
        /* configure mockito behaviour */
        when(userService.findByFullName("John Test")).thenReturn(Optional.of(user));
        /* get the result and make sure its what was expected */
        Optional<User> foundUser = userService.findByFullName("John Test");
        assertThat(foundUser).isPresent();
        assertThat(foundUser).contains(user);
        /* make sure that the repository function was actually called */
        verify(userService,times(1)).findByFullName("John Test");
    }

    @Test
    void testFindByEmail() {
        /* configure mockito behaviour */
        when(userService.findByEmail("alice@mail.com")).thenReturn(Optional.of(user2));
        /* get the result and make sure its what was expected */
        Optional<User> foundUser = userService.findByEmail("alice@mail.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser).contains(user2);
        /* make sure that the repository function was actually called */
        verify(userService,times(1)).findByEmail("alice@mail.com");
    }

    @Test
    void testFindByRoles_Name() {
        /* create the list of users and set up the mockito behaviour */
        List<User> studentUsers = List.of(user);
        List<User> employerUsers = List.of(user2);
        when(userService.findByRoles_Name("STUDENT")).thenReturn(studentUsers);
        when(userService.findByRoles_Name("EMPLOYER")).thenReturn(employerUsers);
        /* get the result and make sure its what was expected */
        List<User> foundStudents = userService.findByRoles_Name("STUDENT");
        List<User> foundEmployers = userService.findByRoles_Name("EMPLOYER");
        assertThat(foundStudents).isNotNull();
        assertThat(foundStudents).hasSize(1);
        assertThat(foundStudents).contains(user);
        assertThat(foundEmployers).isNotNull();
        assertThat(foundEmployers).hasSize(1);
        assertThat(foundEmployers).contains(user2);
        /* make sure the repository method was actually called */
        verify(userService, times(1)).findByRoles_Name("STUDENT");
        verify(userService, times(1)).findByRoles_Name("EMPLOYER");
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