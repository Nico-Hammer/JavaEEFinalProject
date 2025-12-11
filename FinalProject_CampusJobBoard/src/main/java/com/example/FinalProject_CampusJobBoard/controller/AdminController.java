package com.example.FinalProject_CampusJobBoard.controller;

import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;
import com.example.FinalProject_CampusJobBoard.exception.JobNotFoundException;
import com.example.FinalProject_CampusJobBoard.service.JobService;
import com.example.FinalProject_CampusJobBoard.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final JobService jobService;
    private final UserService userService;

    public AdminController(JobService jobService, UserService userService) {
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

    // Decline job posting
    @PostMapping("/pending-jobs/{id}/decline")
    public String declineJobPosting(@PathVariable Long id, @ModelAttribute("job") Job declinedJob){
        Job foundJob = jobService.findById(id);
        if(foundJob == null){
            throw new JobNotFoundException("Job with ID " + id + " not found");
        }
        if(declinedJob.getStatus() != JobStatus.REJECTED) {
            foundJob.setStatus(JobStatus.REJECTED);
        }
        jobService.saveJob(foundJob);
        return "redirect:/admin/pending-jobs";
    }

    // Accept job posting
    @PostMapping("/pending-jobs/{id}/decline")
    public String acceptJobPosting(@PathVariable Long id, @ModelAttribute("job") Job acceptedJob){
        Job foundJob = jobService.findById(id);
        if(foundJob == null){
            throw new JobNotFoundException("Job with ID " + id + " not found");
        }
        if(acceptedJob.getStatus() != JobStatus.APPROVED) {
            foundJob.setStatus(JobStatus.APPROVED);
        }
        jobService.saveJob(foundJob);
        return "redirect:/admin/pending-jobs";
    }
}
