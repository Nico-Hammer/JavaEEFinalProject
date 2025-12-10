package com.example.FinalProject_CampusJobBoard.controller;

import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;
import com.example.FinalProject_CampusJobBoard.exception.JobNotFoundException;
import com.example.FinalProject_CampusJobBoard.service.ApplicationService;
import com.example.FinalProject_CampusJobBoard.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final JobService jobService;
    private final ApplicationService applicationService;

    public StudentController(JobService jobService, ApplicationService applicationService) {
        this.jobService = jobService;
        this.applicationService = applicationService;
    }

    @GetMapping("/jobs")
    public String listApprovedJobs(Model model) {
        List<Job> approvedJobs = jobService.findByStatus(JobStatus.APPROVED);
        model.addAttribute("jobs", approvedJobs);
        return "students/jobs";
    }

    @GetMapping("/jobs/{id}")
    public String viewJobDetails(@PathVariable Long id, Model model){
        Job job = jobService.findById(id);
        if (job == null) {
            throw new JobNotFoundException("Job with ID " + id + " not found");
        }
        if (job.getStatus() != JobStatus.APPROVED) {
            throw new JobNotFoundException("Job is not available");
        }
        model.addAttribute("job", job);
        return "student/job-details";
    }
}
