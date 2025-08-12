package com.hourlyrecruit.controller;

import com.hourlyrecruit.entity.Candidate;
import com.hourlyrecruit.entity.Job;
import com.hourlyrecruit.entity.User;
import com.hourlyrecruit.repository.CandidateRepository;
import com.hourlyrecruit.repository.JobRepository;
import com.hourlyrecruit.repository.UserRepository;
import com.hourlyrecruit.security.Role;
import com.hourlyrecruit.service.CandidateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private CandidateRepository candidateRepo;
    
    @Autowired
    private CandidateService candidateService;

    // 1. View all jobs
    @GetMapping("/jobs")
    public List<Job> viewAllJobs() {
        return jobRepo.findAll();
    }

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<String> applyForJob(@PathVariable Long jobId, Authentication authentication) {
        String email = authentication.getName();

        Candidate candidate = candidateRepo.findByEmail(email).orElse(null);
        if (candidate == null) {
            return ResponseEntity.status(403).body("Access Denied or Candidate Not Found");
        }

        Job job = jobRepo.findById(jobId).orElse(null);
        if (job == null) {
            return ResponseEntity.badRequest().body("Job not found");
        }

        // Avoid duplicate applications
        if (candidate.getAppliedJobs().contains(job)) {
            return ResponseEntity.badRequest().body("Already applied to this job");
        }

        candidate.getAppliedJobs().add(job);  // ✅ Only this is needed

        candidateRepo.save(candidate);  // ✅ Persist from owning side

        return ResponseEntity.ok("Job applied successfully");
    }

    @GetMapping("/my-applications")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<Set<Job>> getMyApplications(Authentication authentication) {
        String email = authentication.getName();

        Candidate candidate = candidateRepo.findByEmail(email).orElse(null);
        if (candidate == null) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(candidate.getAppliedJobs());
    }

    
    @PostMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    public Candidate createProfile(@RequestBody Candidate candidate, Principal principal) {
        String email = principal.getName();
        return candidateService.createProfile(email, candidate);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    public Candidate updateProfile(@RequestBody Candidate updatedCandidate, Principal principal) {
    	String email = principal.getName();
    	
    	return candidateService.updateProfile(email, updatedCandidate);
    	
    }
    
    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    public Candidate viewProfile(Principal principal) {
        String email = principal.getName();
        return candidateService.getProfileByEmail(email);
    }
}
