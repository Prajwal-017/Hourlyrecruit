package com.hourlyrecruit.repository;

import com.hourlyrecruit.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findByCandidateId(Long candidateId);
    List<Interview> findByJobId(Long jobId);
}
