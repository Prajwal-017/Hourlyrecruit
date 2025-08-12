package com.hourlyrecruit.service;

import java.util.List;

import com.hourlyrecruit.entity.Candidate;
import com.hourlyrecruit.entity.Job;

public interface CandidateService {
	
	public Candidate createProfile(String email, Candidate candidate);

	public Candidate updateProfile(String email, Candidate updatedCandidate);

	public Candidate getProfileByEmail(String email);
	
	List<Job> matchJobsForCandidate(Candidate candidate);

	public Candidate getById(Long id);

}
