package com.hourlyrecruit.service;

import com.hourlyrecruit.entity.Interview;

import java.util.List;


public interface InterviewService {
    Interview scheduleInterview(Interview interview);
    List<Interview> getByCandidateId(Long candidateId);
    Interview updateInterview(Long id, Interview updatedInterview);
}

