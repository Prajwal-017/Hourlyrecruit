package com.hourlyrecruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hourlyrecruit.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
	

}
