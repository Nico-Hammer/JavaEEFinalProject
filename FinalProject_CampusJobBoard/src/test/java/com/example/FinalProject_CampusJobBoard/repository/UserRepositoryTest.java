package com.example.FinalProject_CampusJobBoard.repository;

import com.example.FinalProject_CampusJobBoard.entity.Role;
import com.example.FinalProject_CampusJobBoard.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest {

    /* Create instances of the repositories needed */
    @Mock
    private UserRepository repo;
    @Mock
    private RoleRepository roleRepo;

    /* Create the global user object and the global role objects */
    private User user;
    private Set<Role> studentRoles;
    private Set<Role> employerRoles;
    private Set<Role> adminRoles;
    private Role userRole;
    private Role studentRole;
    private Role employerRole;
    private Role adminRole;

    /* Set up the base user info and roles sets before each test */
    @BeforeEach
    void setupUser() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        user = new User();
        user.setFullName("John Test");
        user.setEmail("John@email.com");
        user.setPassword("SomePassword");

        studentRoles = new HashSet<>();
        employerRoles = new HashSet<>();
        adminRoles = new HashSet<>();

        userRole = new Role();
        studentRole = new Role();
        employerRole = new Role();
        adminRole = new Role();

        userRole.setName("USER");
        studentRole.setName("STUDENT");
        employerRole.setName("EMPLOYER");
        adminRole.setName("ADMIN");

        studentRoles.add(userRole);
        studentRoles.add(studentRole);
        employerRoles.add(userRole);
        employerRoles.add(employerRole);
        adminRoles.add(userRole);
        adminRoles.add(adminRole);
    }

    @AfterEach
    void teardownUser() {
        user = null;
        studentRoles = null;
        employerRoles = null;
        adminRoles = null;
    }

    @Test
    void testSaveAndFindByFullName() {
        when(repo.save(user)).thenReturn(user);
        when(repo.findByFullName("John Test")).thenReturn(Optional.of(user));

        /* Save the user */
        User savedUser = repo.save(user);
        assertThat(savedUser.getUser_id()).isNull();

        /* Find the user */
        Optional<User> foundUser = repo.findByFullName("John Test");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getFullName()).isEqualTo("John Test");

        /* make sure the repository methods were called */
        verify(repo, times(1)).save(user);
        verify(repo, times(1)).findByFullName("John Test");
    }

    @Test
    void testSaveAndFindByEmail() {
        when(repo.save(user)).thenReturn(user);
        when(repo.findByEmail("John@email.com")).thenReturn(Optional.of(user));

        /* Save the user */
        User savedUser = repo.save(user);
        assertThat(savedUser.getUser_id()).isNull();

        /* Find the user by email */
        Optional<User> foundUser = repo.findByEmail("John@email.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("John@email.com");

        /* make sure the repository methods were called */
        verify(repo, times(1)).save(user);
        verify(repo, times(1)).findByEmail("John@email.com");
    }

    @Test
    void testSaveAndFindByRoles_Name() {
        /* Create admin and employer user objects */
        User admin = new User();
        User employer = new User();

        admin.setRoles(adminRoles);
        employer.setRoles(employerRoles);

        /* Set up user info */
        admin.setFullName("Admin");
        admin.setEmail("admin@mail.com");
        admin.setPassword("adminPass");

        employer.setFullName("Employer");
        employer.setEmail("employer@mail.com");
        employer.setPassword("employerPass");

        /* Mock the repository behavior */
        when(repo.save(user)).thenReturn(user);
        when(repo.save(admin)).thenReturn(admin);
        when(repo.save(employer)).thenReturn(employer);
        when(repo.findByRoles_Name("STUDENT")).thenReturn(List.of(user));
        when(repo.findByRoles_Name("EMPLOYER")).thenReturn(List.of(employer));
        when(repo.findByRoles_Name("ADMIN")).thenReturn(List.of(admin));

        /* Save the users */
        User savedStudent = repo.save(user);
        User savedEmployer = repo.save(employer);
        User savedAdmin = repo.save(admin);

        /* find the users based on roles */
        List<User> studentsWithRole = repo.findByRoles_Name("STUDENT");
        List<User> employersWithRole = repo.findByRoles_Name("EMPLOYER");
        List<User> adminsWithRole = repo.findByRoles_Name("ADMIN");

        /* make sure that users are found and have the expected roles */
        assertThat(studentsWithRole).isNotEmpty();
        assertThat(employersWithRole).isNotEmpty();
        assertThat(adminsWithRole).isNotEmpty();
        assertThat(studentsWithRole).contains(savedStudent);
        assertThat(employersWithRole).contains(savedEmployer);
        assertThat(adminsWithRole).contains(savedAdmin);

        /* make sure the repository methods were called */
        verify(repo, times(1)).findByRoles_Name("STUDENT");
        verify(repo, times(1)).findByRoles_Name("EMPLOYER");
        verify(repo, times(1)).findByRoles_Name("ADMIN");
    }
}