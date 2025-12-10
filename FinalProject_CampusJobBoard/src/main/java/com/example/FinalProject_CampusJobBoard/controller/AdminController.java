package com.example.FinalProject_CampusJobBoard.controller;

import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.service.ApplicationService;
import com.example.FinalProject_CampusJobBoard.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ApplicationService applicationService;
    private final UserService userService;

    public AdminController(ApplicationService applicationService, UserService userService) {
        this.applicationService = applicationService;
        this.userService = userService;
    }

    // Display all users
    @GetMapping("/users")
    public String listAllUsers(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users",users);
        return "admin/users";
    }
}
