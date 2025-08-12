package com.hourlyrecruit.service;

import java.util.List;

import com.hourlyrecruit.entity.Job;

public interface JobService {
    Job createJob(Job job);
    Job updateJob(Long id, Job job);
    String deleteJob(Long id);
    List<Job> getAllJobs(); // for reading
    
    List<Job> filterJobsBySkill(String skill);
}
