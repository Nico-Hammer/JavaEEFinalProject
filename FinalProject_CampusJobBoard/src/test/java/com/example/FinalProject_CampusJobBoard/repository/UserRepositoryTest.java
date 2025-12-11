package com.example.FinalProject_CampusJobBoard.repository;

import com.example.FinalProject_CampusJobBoard.entity.Role;
import com.example.FinalProject_CampusJobBoard.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository repo;
    @Autowired
    private RoleRepository roleRepo;

    User user = new User();
    Set<Role> studentRoles = new HashSet<>();
    Set<Role> employerRoles = new HashSet<>();
    Set<Role> adminRoles = new HashSet<>();
    Role userRole = new Role();
    Role studentRole = new Role();
    Role employerRole = new Role();
    Role adminRole = new Role();
    @BeforeEach
    void setupUser(){
        repo.deleteAll();
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
        user.setFullName("John Test");
        user.setEmail("John@email.com");
        user.setPassword("SomePassword");
    }
    @AfterEach
    void teardownUser(){
        user = null;
        studentRoles = null;
        employerRoles = null;
        adminRoles = null;
        repo.deleteAll();
    }
    @Test
    void testSaveAndFindByFullName() {
        User savedUser = repo.save(user);
        assertThat(savedUser.getUser_id()).isNotNull();
        User foundUser = repo.findByFullName(savedUser.getFullName()).orElse(null);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getFullName()).isEqualTo("John Test");
        System.out.println("SaveAndFindByName executed successfully with name of " + foundUser.getFullName());
    }

    @Test
    void testSaveAndFindByEmail() {
        User savedUser = repo.save(user);
        assertThat(savedUser.getUser_id()).isNotNull();
        User foundUser = repo.findByEmail(savedUser.getEmail()).orElse(null);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("John@email.com");
        System.out.println("SaveAndFindByEmail executed successfully with email of " + foundUser.getEmail());

    }

    @Test
    void findByRoles_Name() {
    }
}