package com.hourlyrecruit.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hourlyrecruit.entity.Candidate;
import com.hourlyrecruit.entity.Job;
import com.hourlyrecruit.repository.CandidateRepository;
import com.hourlyrecruit.repository.JobRepository;

@Service
public class CandidateServiceImpl implements CandidateService {
	
	@Autowired
	private CandidateRepository candidateRepo;
	
	@Autowired
	private JobRepository jobRepo;

	@Override
	public Candidate createProfile(String email, Candidate candidateData) {
		// TODO Auto-generated method stub
		
		Optional<Candidate> existing =  candidateRepo.findByEmail(email);
		
		if(existing.isPresent()) {
			throw new RuntimeException("Profile already existed");
		}
		
		candidateData.setEmail(email);
		return candidateRepo.save(candidateData);
	}

	@Override
	public Candidate updateProfile(String email, Candidate updatedCandidate) {
	    Candidate existing = candidateRepo.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("Candidate profile not found"));

	    // Only update resumeUrl and skills
	    existing.setResumeUrl(updatedCandidate.getResumeUrl());
	    existing.setSkills(updatedCandidate.getSkills());

	    return candidateRepo.save(existing);
	}


	@Override
	public Candidate getProfileByEmail(String email) {
	    return candidateRepo.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("Candidate profile not found"));
	}

	@Override
    public List<Job> matchJobsForCandidate(Candidate candidate) {
        List<Job> allJobs = jobRepo.findAll();
        return allJobs.stream()
                .filter(job -> job.getRequiredSkills() != null &&
                        !Collections.disjoint(candidate.getSkills(), job.getRequiredSkills()))
                .collect(Collectors.toList());
    }

	 @Override
	    public Candidate getById(Long id) {
	        return candidateRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Candidate not found with id " + id));
	    }


}
