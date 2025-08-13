package com.hourlyrecruit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hourlyrecruit.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

	// CandidateRepository.java
	Optional<Candidate> findByEmail(String email); // ✅ CORRECT hai

}
