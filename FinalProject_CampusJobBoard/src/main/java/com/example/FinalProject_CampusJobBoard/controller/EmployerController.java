package com.example.FinalProject_CampusJobBoard.controller;

import com.example.FinalProject_CampusJobBoard.Security.user.CustomUserDetails;
import com.example.FinalProject_CampusJobBoard.Security.user.CustomUserDetailsService;
import com.example.FinalProject_CampusJobBoard.entity.Job;
import com.example.FinalProject_CampusJobBoard.entity.JobApplication;
import com.example.FinalProject_CampusJobBoard.entity.User;
import com.example.FinalProject_CampusJobBoard.enums.JobStatus;
import com.example.FinalProject_CampusJobBoard.exception.JobNotFoundException;
import com.example.FinalProject_CampusJobBoard.exception.UnauthorizedUserException;
import com.example.FinalProject_CampusJobBoard.service.ApplicationService;
import com.example.FinalProject_CampusJobBoard.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/employer")
public class EmployerController {
    private final JobService jobService;
    private final ApplicationService applicationService;
    private final CustomUserDetailsService customUserDetailsService;

    public EmployerController(JobService jobService, ApplicationService applicationService, CustomUserDetailsService customUserDetailsService) {
        this.jobService = jobService;
        this.applicationService = applicationService;
        this.customUserDetailsService = customUserDetailsService;
    }

    // Display all jobs
    @GetMapping("/myjobs")
    public String listMyJobs(Model model) {
        User employer = customUserDetailsService.getCurrentUser();
        List<Job> jobs = jobService.findByEmployer(employer);
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
        User employer = customUserDetailsService.getCurrentUser();
        job.setEmployer(employer);

        jobService.saveJob(job);
        return "redirect:/employer/myjobs";
    }

    // Display edit for an existing job
    @GetMapping("/jobs/{id}/edit")
    public String showEditJobForm(@PathVariable Long id, Model model) {
        User employer = customUserDetailsService.getCurrentUser();
        Job job = jobService.findById(id);
        if (job == null) {
            throw new JobNotFoundException("Job with ID " + id + " not found");
        }

        // Verify employer ownership
        if (!job.getEmployer().getUser_id().equals(employer.getUser_id())){
            throw new UnauthorizedUserException("You can only edit your own jobs");
        }

        model.addAttribute("job", job);
        return "employer/job-form";
    }

    // Update existing job
    @PostMapping("/jobs/{id}/edit")
    public String updateJob(@PathVariable Long id, @ModelAttribute("job") Job updatedJob) {
        User employer = customUserDetailsService.getCurrentUser();
        Job existingJob = jobService.findById(id);
        if (existingJob == null) {
            throw new JobNotFoundException("Job with ID " + id + " not found");
        }

        // Verify employer ownership
        if (!existingJob.getEmployer().getUser_id().equals(employer.getUser_id())){
            throw new UnauthorizedUserException("You can only edit your own jobs");
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
        User employer = customUserDetailsService.getCurrentUser();
        Job job = jobService.findById(id);
        if (job == null) {
            throw new JobNotFoundException("Job with ID " + id + " not found");
        }

        // Verify employer ownership
        if (!job.getEmployer().getUser_id().equals(employer.getUser_id())){
            throw new UnauthorizedUserException("You can only edit your own jobs");
        }

        jobService.deleteById(id);
        return "redirect:/employer/myjobs";
    }

    // Display all applications
    @GetMapping("/applications")
    public String viewAllApplications(Model model) {
        User employer = customUserDetailsService.getCurrentUser();
        List<Job> employerJobs = jobService.findByEmployer(employer);

        List<JobApplication> applications = employerJobs.stream()
                        .flatMap(job -> applicationService.findByJob(job).stream())
                                .collect(Collectors.toList());

        model.addAttribute("applications", applications);
        return "employer/applicants";
    }
}
