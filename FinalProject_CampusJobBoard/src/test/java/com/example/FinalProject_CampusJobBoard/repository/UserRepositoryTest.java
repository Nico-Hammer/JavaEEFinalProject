package com.example.FinalProject_CampusJobBoard.repository;

import com.example.FinalProject_CampusJobBoard.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository repo;

    User user = new User();
    @BeforeEach
    void setupUser(){
        repo.deleteAll();
        user.setFullName("John Test");
        user.setEmail("John@email.com");
        user.setPassword("SomePassword");
    }
    @AfterEach
    void teardownUser(){
        user = null;
        repo.deleteAll();
    }
    @Test
    void testSaveAndFindByFullName() {
        User savedUser = repo.save(user);
        assertThat(savedUser.getUser_id()).isNotNull();
        User foundUser = repo.findByFullName(savedUser.getFullName()).orElse(null);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getFullName()).isEqualTo("John Test");
        System.out.println("SaveAndFindByName execute successfully with name of " + foundUser.getFullName());
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findByRoles_Name() {
    }
}