package com.hourlyrecruit.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hourlyrecruit.entity.Job;
import com.hourlyrecruit.repository.JobRepository;

@Service
public class JobServiceImpl implements JobService{
	
	@Autowired
	private JobRepository jobrepo;

	public Job createJob(Job job) {
		// TODO Auto-generated method stub
		return jobrepo.save(job);
	}

	public String deleteJob(Long id) {
		// TODO Auto-generated method stub
		Optional<Job> job = jobrepo.findById(id);
		
		if(job.isPresent()) {
			jobrepo.deleteById(id);
			
			return "Job with id "+ id +" deleted Successfully";
		}else {
			return "Job did not found with id " + id ;
		}
	}

	public Job updateJob(Long id, Job updatedjob) {
		// TODO Auto-generated method stub
		Job existingjob = jobrepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Job not found with the job id "+id));
		
		existingjob.setTitle(updatedjob.getTitle());
		existingjob.setDescription(updatedjob.getDescription());
		existingjob.setLocation(updatedjob.getLocation());
		
		return jobrepo.save(existingjob);
	}

	public List<Job> getAllJobs() {
		// TODO Auto-generated method stub
		return jobrepo.findAll();
	}

	 @Override
	    public List<Job> filterJobsBySkill(String skill) {
	        return jobrepo.findAll().stream()
	                .filter(job -> job.getRequiredSkills() != null &&
	                               job.getRequiredSkills().contains(skill))
	                .collect(Collectors.toList());
	    }
	
	
	

}
