package com.hourlyrecruit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hourlyrecruit.entity.Candidate;
import com.hourlyrecruit.entity.Job;
import com.hourlyrecruit.service.CandidateService;
import com.hourlyrecruit.service.JobService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api/jobs")
public class JobController {
	
	@Autowired
	private JobService jobservice;
	
	@Autowired
	private CandidateService candidateService;
	
	@PostMapping
	public Job createJob(@RequestBody Job job) {
		return jobservice.createJob(job);
		
	}
	
	@DeleteMapping("/{id}")
	public String deleteJob(@PathVariable Long id) {
		return jobservice.deleteJob(id);
	}
	
	@PutMapping("/{id}")
	public Job updateJob(@PathVariable Long id, @RequestBody Job updatedjob) {
		//TODO: process PUT request
		
		return jobservice.updateJob(id, updatedjob);
	}
	
	@GetMapping
	public List<Job> getAllJobs() {
		return jobservice.getAllJobs();
	}
	
	@GetMapping("/match/{id}")
	@PreAuthorize("hasRole('CANDIDATE')")
	public List<Job> getMatchingJobs(@PathVariable Long id) {
	    Candidate candidate = candidateService.getById(id);
	    return candidateService.matchJobsForCandidate(candidate);
	}
	
	
}
