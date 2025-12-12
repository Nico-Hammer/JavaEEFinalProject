package com.example.FinalProject_CampusJobBoard.service;

import com.example.FinalProject_CampusJobBoard.entity.Role;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.repository.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RoleServiceTest {

    @MockitoBean
    private RoleRepository repo;
    @MockitoBean
    private RoleService service;

    private Role userRole;
    private Role adminRole;

    private String newRoleName = "NEW";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRole = new Role();
        adminRole = new Role();
        userRole.setName("USER");
        adminRole.setName("ADMIN");
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
        userRole = null;
        adminRole = null;
    }

    @Test
    void findByName() {
        /* configure mockito behaviour */
        when(service.findByName("STUDENT")).thenReturn(Optional.of(userRole));
        when(service.findByName("ADMIN")).thenReturn(Optional.of(adminRole));
        /* get the result and make sure its what was expected */
        Optional<Role> foundUser = service.findByName("STUDENT");
        assertThat(foundUser).isPresent();
        assertThat(foundUser).contains(userRole);
        Optional<Role> foundAdmin = service.findByName("ADMIN");
        assertThat(foundAdmin).isPresent();
        assertThat(foundAdmin).contains(adminRole);
        /* make sure that the repository function was actually called */
        verify(service,times(1)).findByName("STUDENT");
        verify(service,times(1)).findByName("ADMIN");
    }

    @Test
    void getOrCreateRole() {
        ///  testing the get role functionality
        /* configure mockito behaviour */
        when(service.getOrCreateRole("STUDENT")).thenReturn(userRole);
        /* get the result and make sure its what was expected */
        Optional<Role> foundUser = Optional.ofNullable(service.getOrCreateRole("STUDENT"));
        assertThat(foundUser).isPresent();
        assertThat(foundUser).contains(userRole);
        /* make sure that the repository function was actually called */
        verify(service,times(1)).getOrCreateRole("STUDENT");
        /// testing the create role functionality
        /* create the new role and configure mockito behaviour */
        Role newRole = new Role();
        newRole.setName(newRoleName);
        when(repo.save(newRole)).thenReturn(newRole);
        when(service.getOrCreateRole("NEW")).thenReturn(newRole);
        /* get the result and make sure its what was expected */
        Optional<Role> createdRole = Optional.ofNullable(service.getOrCreateRole(newRoleName));
        assertThat(createdRole).isPresent();
        assertThat(createdRole.get().getName()).isEqualTo(newRoleName);
        /* make sure the repository function was actually called */
        verify(service,times(1)).getOrCreateRole(newRoleName);
    }
}