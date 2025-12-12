package com.example.FinalProject_CampusJobBoard.controller;

import com.example.FinalProject_CampusJobBoard.Security.user.CustomUserDetailsService;
import com.example.FinalProject_CampusJobBoard.entity.Role;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.repository.RoleRepository;
import com.example.FinalProject_CampusJobBoard.repository.UserRepository;
import com.example.FinalProject_CampusJobBoard.service.RoleService;
import com.example.FinalProject_CampusJobBoard.service.UserService;
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
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(CustomUserDetailsService userDetailsService, UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.roleService = roleService;
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

        if (userService.findByEmail(user.getEmail()).isPresent()) {
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

        Role userRole = roleService.getOrCreateRole(roleName);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(userRole));

        userService.save(user);
        return "redirect:/jobBoard/login?registered=true";
    }

    @GetMapping("/login")
    public String login(){
        return "public/login";
    }
}