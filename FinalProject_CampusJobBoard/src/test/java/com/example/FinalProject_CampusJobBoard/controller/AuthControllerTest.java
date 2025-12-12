package com.example.FinalProject_CampusJobBoard.controller;

import com.example.FinalProject_CampusJobBoard.Security.user.CustomUserDetailsService;
import com.example.FinalProject_CampusJobBoard.entity.Role;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.service.RoleService;
import com.example.FinalProject_CampusJobBoard.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private RoleService roleService;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private Role studentRole;

    @BeforeEach
    void setUp() {
        // Set up test user
        testUser = new User();
        testUser.setUser_id(1L);
        testUser.setFullName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword");
        testUser.setEnabled(true);

        // Set up test role
        studentRole = new Role();
        studentRole.setId(1L);
        studentRole.setName("STUDENT");
        testUser.setRoles(Collections.singleton(studentRole));
    }

    @AfterEach
    void tearDown(){
        reset(userDetailsService, userService, roleService, passwordEncoder);

        testUser = null;
        studentRole = null;
    }

    @Test
    void testShowHomePage() throws Exception{
        mockMvc.perform(get("/jobBoard/"))
                .andExpect(status().isOk())
                .andExpect(view().name("public/home"));
    }

    @Test
    void testShowHomePageAlt() throws Exception{
        mockMvc.perform(get("/jobBoard/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("public/home"));
    }

    @Test
    void testShowRegisterForm() throws Exception{
        mockMvc.perform(get("/jobBoard/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("public/register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void testShowLoginForm() throws Exception {

    }

    @Test
    void testRegistrationSuccess_Student() throws Exception{

    }

    @Test
    void testRegistrationSuccess_Employer() throws Exception{

    }

    @Test
    void testRegisterWithDuplicateEmail() throws Exception{

    }

    @Test
    void testRegisterWithInvalidRole_DefaultToStudent() throws Exception{

    }

    @Test
    void testRegisterWithValidationErrors() throws Exception{

    }
}
