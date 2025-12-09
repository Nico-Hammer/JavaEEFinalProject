package com.example.FinalProject_CampusJobBoard.controller;

import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.JobApplication;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;
import com.example.FinalProject_CampusJobBoard.exception.JobNotFoundException;
import com.example.FinalProject_CampusJobBoard.service.ApplicationService;
import com.example.FinalProject_CampusJobBoard.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/employer")
public class EmployerController {
    private final JobService jobService;
    private final ApplicationService applicationService;

    public EmployerController(JobService jobService, ApplicationService applicationService) {
        this.jobService = jobService;
        this.applicationService = applicationService;
    }

    // Display all jobs
    @GetMapping("/myjobs")
    public String listMyJobs(Model model) {
        List<Job> jobs = jobService.findAll();
        model.addAttribute("jobs", jobs);
        return "employer/my-jobs";
    }

    // Display form to create new job
    @GetMapping("/jobs/new")
    public String showCreateJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "employer/job-form";
    }

    // Create new job
    @PostMapping("/jobs")
    public String createJob(@ModelAttribute("job") Job job) {
        job.setStatus(JobStatus.PENDING);
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());

        jobService.saveJob(job);
        return "redirect:/employer/myjobs";
    }

    // Display edit for an existing job
    @GetMapping("/jobs/{id}/edit")
    public String showEditJobForm(@PathVariable Long id, Model model) {
        Job job = jobService.findById(id);
        if (job == null) {
            throw new JobNotFoundException("Job with ID " + id + " not found");
        }

        model.addAttribute("job", job);
        return "employer/job-form";
    }

    // Update existing job
    @PostMapping("/jobs/{id}/edit")
    public String updateJob(@PathVariable Long id, @ModelAttribute("job") Job updatedJob) {
        Job existingJob = jobService.findById(id);
        if (existingJob == null) {
            throw new JobNotFoundException("Job with ID " + id + " not found");
        }

        // Update job fields
        existingJob.setTitle(updatedJob.getTitle());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setSalary(updatedJob.getSalary());
        existingJob.setCategory(updatedJob.getCategory());
        existingJob.setDeadline(updatedJob.getDeadline());
        existingJob.setUpdatedAt(LocalDateTime.now());

        // Set to PENDING if job REJECTED
        if (existingJob.getStatus() == JobStatus.REJECTED) {
            existingJob.setStatus(JobStatus.PENDING);
        }

        jobService.saveJob(existingJob);
        return "redirect:/employer/myjobs";
    }

    // Delete existing job
    @PostMapping("/jobs/{id}/delete")
    public String deleteJob(@PathVariable Long id) {
        Job job = jobService.findById(id);
        if (job == null) {
            throw new JobNotFoundException("Job with ID " + id + " not found");
        }

        jobService.deleteById(id);
        return "redirect:/employer/myjobs";
    }

    // Display all applications
    @GetMapping("/applications")
    public String viewAllApplications(Model model) {
        List<JobApplication> applications = applicationService.findAll();
        model.addAttribute("applications", applications);
        return "employer/applicants";
    }
}
