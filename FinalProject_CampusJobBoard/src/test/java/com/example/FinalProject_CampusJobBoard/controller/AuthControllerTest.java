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

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc.perform(get("/jobBoard/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("public/register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void testRegistrationSuccess_Student() throws Exception{
        // Set up the mocks
        when(userService.findByEmail(anyString())).thenReturn(Optional.empty());
        when(roleService.getOrCreateRole("STUDENT")).thenReturn(studentRole);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userService.save(any(User.class))).thenReturn(testUser);

        // Perform the registration
        mockMvc.perform(post("jobBoard/register")
                .param("fullName", "Test User")
                .param("email", "test@example.com")
                .param("password", "password123")
                .param("roleType", "STUDENT"))
                .andExpect(status().isOk())
                .andExpect(view().name("User registered successfully!"));

        // Verify the interactions
        verify(userService, times(1)).findByEmail("test@example.com");
        verify(roleService, times(1)).getOrCreateRole("STUDENT");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void testRegistrationSuccess_Employer() throws Exception{
        Role employerRole = new Role();
        employerRole.setId(2L);
        employerRole.setName("EMPLOYER");

        when(userService.findByEmail(anyString())).thenReturn(Optional.empty());
        when(roleService.getOrCreateRole("EMPLOYER")).thenReturn(employerRole);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userService.save(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("jobBoard/register")
                        .param("fullName", "Employer User")
                        .param("email", "employer@example.com")
                        .param("password", "password123")
                        .param("roleType", "EMPLOYER"))
                .andExpect(status().isOk());

        verify(roleService, times(1)).getOrCreateRole("EMPLOYER");
    }

    @Test
    void testRegisterWithDuplicateEmail() throws Exception{
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        mockMvc.perform(post("/jobBoard/register")
                        .param("fullName", "Test User")
                        .param("email", "test@example.com")
                        .param("password", "password123")
                        .param("roleType", "STUDENT"))
                .andExpect(status().isOk())
                .andExpect(view().name("public/register"));

        verify(userService, times(1)).findByEmail("test@example.com");
        verify(userService, never()).save(any(User.class));
    }

    @Test
    void testRegisterWithInvalidRole_DefaultToStudent() throws Exception{
        when(userService.findByEmail(anyString())).thenReturn(Optional.empty());
        when(roleService.getOrCreateRole("STUDENT")).thenReturn(studentRole);
        when(passwordEncoder.encode(anyString())).thenReturn("encodePassword");
        when(userService.save(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/jobBoard/register")
                        .param("fullName", "Test User")
                        .param("email", "test@example.com")
                        .param("password", "password123")
                        .param("roleType", "INVALID_ROLE"))
                .andExpect(status().isOk());

        verify(roleService, times(1)).getOrCreateRole("Student");
    }

    @Test
    void testRegisterWithValidationErrors() throws Exception{

    }
}
