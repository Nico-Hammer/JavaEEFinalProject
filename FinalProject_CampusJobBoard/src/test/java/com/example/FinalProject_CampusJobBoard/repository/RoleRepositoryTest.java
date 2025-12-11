package com.example.FinalProject_CampusJobBoard.repository;

import com.example.FinalProject_CampusJobBoard.entity.Role;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@DataJpaTest
class RoleRepositoryTest {

    @Mock
    private RoleRepository repo;

    private Role userRole;
    private Role studentRole;
    private Role employerRole;
    private Role adminRole;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);  // Initialize Mockito mocks

        userRole = new Role();
        studentRole = new Role();
        employerRole = new Role();
        adminRole = new Role();

        userRole.setName("USER");
        studentRole.setName("STUDENT");
        employerRole.setName("EMPLOYER");
        adminRole.setName("ADMIN");
    }

    @AfterEach
    void teardown(){
        userRole = null;
        studentRole = null;
        employerRole = null;
        adminRole = null;
        repo.deleteAll();
    }

    @Test
    void testFindByName() {
        /* mock the repository method and return results */
        when(repo.findByName("USER")).thenReturn(Optional.of(userRole));
        when(repo.findByName("STUDENT")).thenReturn(Optional.of(studentRole));
        when(repo.findByName("EMPLOYER")).thenReturn(Optional.of(employerRole));
        when(repo.findByName("ADMIN")).thenReturn(Optional.of(adminRole));

        /* call the repository method and save the results */
        Optional<Role> foundUserRole = repo.findByName("USER");
        Optional<Role> foundStudentRole = repo.findByName("STUDENT");
        Optional<Role> foundEmployerRole = repo.findByName("EMPLOYER");
        Optional<Role> foundAdminRole = repo.findByName("ADMIN");

        /* make sure that we got the expected results */
        assertThat(foundUserRole).isPresent();
        assertThat(foundUserRole.get()).isEqualTo(userRole);
        assertThat(foundStudentRole).isPresent();
        assertThat(foundStudentRole.get()).isEqualTo(studentRole);
        assertThat(foundEmployerRole).isPresent();
        assertThat(foundEmployerRole.get()).isEqualTo(employerRole);
        assertThat(foundAdminRole).isPresent();
        assertThat(foundAdminRole.get()).isEqualTo(adminRole);

        /* make sure that the repository method was actually called */
        verify(repo).findByName("USER");
        verify(repo).findByName("STUDENT");
        verify(repo).findByName("EMPLOYER");
        verify(repo).findByName("ADMIN");
    }
}