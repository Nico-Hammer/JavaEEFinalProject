package com.example.FinalProject_CampusJobBoard.controller;

import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;
import com.example.FinalProject_CampusJobBoard.service.ApplicationService;
import com.example.FinalProject_CampusJobBoard.service.JobService;
import com.example.FinalProject_CampusJobBoard.service.JobServiceImpl;
import com.example.FinalProject_CampusJobBoard.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final JobServiceImpl jobService;
    private final UserService userService;

    public AdminController(JobServiceImpl jobService, UserService userService) {
        this.jobService = jobService;
        this.userService = userService;
    }

    // Display all users
    @GetMapping("/users")
    public String listAllUsers(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users",users);
        return "admin/users";
    }

    // Display all pending jobs
    @GetMapping("/pending-jobs")
    public String listAllPending(Model model){
        List<Job> pendingJobs = jobService.findByStatus(JobStatus.PENDING);
        model.addAttribute("pendingJobs",pendingJobs);
        return "admin/pending-jobs";
    }
}
