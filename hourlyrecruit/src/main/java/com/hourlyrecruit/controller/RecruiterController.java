package com.hourlyrecruit.controller;

import com.hourlyrecruit.entity.Job;
import com.hourlyrecruit.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {

    @Autowired
    private JobRepository jobRepository;

    // Create Job
    @PostMapping("/jobs")
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job savedJob = jobRepository.save(job);
        return ResponseEntity.ok(savedJob);
    }

    // Get all jobs
    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return ResponseEntity.ok(jobs);
    }

    // Delete job
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        jobRepository.deleteById(id);
        return ResponseEntity.ok("Job deleted successfully");
    }
}


