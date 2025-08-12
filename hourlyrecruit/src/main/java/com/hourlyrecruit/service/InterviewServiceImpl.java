package com.hourlyrecruit.service;

import com.hourlyrecruit.entity.Candidate;
import com.hourlyrecruit.entity.Interview;
import com.hourlyrecruit.entity.Job;
import com.hourlyrecruit.repository.CandidateRepository;
import com.hourlyrecruit.repository.InterviewRepository;
import com.hourlyrecruit.repository.JobRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private InterviewRepository interviewRepo;

    @Autowired
    private CandidateRepository candidateRepo;
    
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JobRepository jobRepo;

    @Override
    public Interview scheduleInterview(Interview interview) {
        Long candidateId = interview.getCandidate().getId();
        Long jobId = interview.getJob().getId();

        Candidate candidate = candidateRepo.findById(candidateId)
            .orElseThrow(() -> new IllegalArgumentException("Candidate not found with ID: " + candidateId));

        Job job = jobRepo.findById(jobId)
            .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

        interview.setCandidate(candidate);
        interview.setJob(job);
        interview.setStatus("Scheduled");

        Interview savedInterview = interviewRepo.save(interview);

        // Send notifications
        notificationService.sendEmail(
            candidate.getEmail(),
            "Interview Scheduled",
            String.format("Your interview for job '%s' is scheduled on %s",
                    job.getTitle(), savedInterview.getDateTime())
        );

        notificationService.sendSMS(
            candidate.getPhoneNumber(),
            String.format("Interview scheduled for job '%s' at %s",
                    job.getTitle(), savedInterview.getDateTime())
        );

        notificationService.sendWhatsApp(
            candidate.getPhoneNumber(),
            String.format("WhatsApp: Your interview for job '%s' is scheduled.",
                    job.getTitle())
        );

        return savedInterview;
    }



    @Override
    public List<Interview> getByCandidateId(Long candidateId) {
        return interviewRepo.findByCandidateId(candidateId);
    }

    @Override
    public Interview updateInterview(Long id, Interview updatedInterview) {
        Interview existing = interviewRepo.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setStatus(updatedInterview.getStatus());
        existing.setMode(updatedInterview.getMode());
        existing.setDateTime(updatedInterview.getDateTime());

        return interviewRepo.save(existing);
    }
}

