package com.example.FinalProject_CampusJobBoard.repository;

import com.example.FinalProject_CampusJobBoard.entity.Role;
import com.example.FinalProject_CampusJobBoard.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    /* create instances of the repositories needed */
    @Autowired
    private UserRepository repo;
    @Autowired
    private RoleRepository roleRepo;

    /* create the global user object and the global role objects */
    User user = new User();
    Set<Role> studentRoles = new HashSet<>();
    Set<Role> employerRoles = new HashSet<>();
    Set<Role> adminRoles = new HashSet<>();
    Role userRole = new Role();
    Role studentRole = new Role();
    Role employerRole = new Role();
    Role adminRole = new Role();

    /* set up the base user info and roles sets before each test */
    @BeforeEach
    void setupUser(){
        /* delete everything and start from a blank slate */
        repo.deleteAll();
        roleRepo.deleteAll();

        user.setFullName("John Test");
        user.setEmail("John@email.com");
        user.setPassword("SomePassword");

        userRole.setName("USER");
        studentRole.setName("STUDENT");
        employerRole.setName("EMPLOYER");
        adminRole.setName("ADMIN");

        userRole = roleRepo.save(userRole);
        studentRole = roleRepo.save(studentRole);
        employerRole = roleRepo.save(employerRole);
        adminRole = roleRepo.save(adminRole);

        studentRoles.add(userRole);
        studentRoles.add(studentRole);
        employerRoles.add(userRole);
        employerRoles.add(employerRole);
        adminRoles.add(userRole);
        adminRoles.add(adminRole);
    }
    /* after each test set everything to null and delete any created info */
    @AfterEach
    void teardownUser(){
        user = null;
        studentRoles = null;
        employerRoles = null;
        adminRoles = null;
        repo.deleteAll();
        roleRepo.deleteAll();
    }

    @Test
    void testSaveAndFindByFullName() {
        /* save the user to the h2 database */
        User savedUser = repo.save(user);
        assertThat(savedUser.getUser_id()).isNotNull(); // make sure it was actually saved
        /* find the user and make sure the data saved is as expected */
        User foundUser = repo.findByFullName(savedUser.getFullName()).orElse(null);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getFullName()).isEqualTo("John Test");
        System.out.println("SaveAndFindByName executed successfully with name of " + foundUser.getFullName());
    }

    @Test
    void testSaveAndFindByEmail() {
        /* save the user to the h2 database */
        User savedUser = repo.save(user);
        assertThat(savedUser.getUser_id()).isNotNull(); // make sure it was actually saved
        /* find the user and make sure the data saved is as expected */
        User foundUser = repo.findByEmail(savedUser.getEmail()).orElse(null);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("John@email.com");
        System.out.println("SaveAndFindByEmail executed successfully with email of " + foundUser.getEmail());

    }

    @Test
    void testSaveAndFindByRoles_Name() {
        /* create admin and employer user objects */
        User admin = new User();
        User employer = new User();

        user.setRoles(studentRoles); // set the student roles

        /* set up the required info for the admin and employer user objects */
        admin.setFullName("Admin");
        admin.setEmail("admin@mail.com");
        admin.setPassword("adminPass");
        admin.setRoles(adminRoles);

        employer.setFullName("Employer");
        employer.setEmail("employer@mail.com");
        employer.setPassword("employerPass");
        employer.setRoles(employerRoles);

        /* save the users to the h2 database and verify that they were actually saved */
        User savedStudent = repo.save(user);
        User savedEmployer = repo.save(employer);
        User savedAdmin = repo.save(admin);
        assertThat(savedStudent.getUser_id()).isNotNull();
        assertThat(savedEmployer.getUser_id()).isNotNull();
        assertThat(savedAdmin.getUser_id()).isNotNull();

        // Test the 'findByRoles_Name' method
        List<User> studentsWithRole = repo.findByRoles_Name("STUDENT");
        List<User> employersWithRole = repo.findByRoles_Name("EMPLOYER");
        List<User> adminsWithRole = repo.findByRoles_Name("ADMIN");

        // Assert that users are found and have the expected roles
        assertThat(studentsWithRole).isNotEmpty();
        assertThat(employersWithRole).isNotEmpty();
        assertThat(adminsWithRole).isNotEmpty();

        // Verify the role assignments for each user
        assertThat(studentsWithRole).contains(savedStudent);
        assertThat(employersWithRole).contains(savedEmployer);
        assertThat(adminsWithRole).contains(savedAdmin);
    }
}