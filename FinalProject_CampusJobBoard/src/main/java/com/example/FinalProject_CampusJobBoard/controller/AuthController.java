package com.example.FinalProject_CampusJobBoard.controller;

import com.example.FinalProject_CampusJobBoard.Security.user.CustomUserDetailsService;
import com.example.FinalProject_CampusJobBoard.entity.Role;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.repository.RoleRepository;
import com.example.FinalProject_CampusJobBoard.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/jobBoard")
public class AuthController {

    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(CustomUserDetailsService userDetailsService, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/")
    public String home() {
        return "public/home";
    }

    @GetMapping("/home")
    public String homePage() {
        return "public/home";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "public/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute @Valid User user, BindingResult bindingResult,
                           @RequestParam(required = false, defaultValue = "STUDENT") String roleType) {
        if (bindingResult.hasErrors()) {
            return "public/register";
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "error.email", "Email already exists");
            return "public/register";
        }

        final String roleName;
        String upperRoleType = roleType.toUpperCase();
        if (upperRoleType.equals("STUDENT") || upperRoleType.equals("EMPLOYER")) {
            roleName = upperRoleType;
        } else {
            roleName = "STUDENT"; // STUDENT role default
        }

        Role userRole = roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    return roleRepository.save(newRole);
                });

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);
        return "User registered successfully!";
    }

    @GetMapping("/login")
    public String login(){
        return "public/login";
    }
}