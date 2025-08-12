package com.hourlyrecruit.controller;

import com.hourlyrecruit.entity.Interview;
import com.hourlyrecruit.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviews")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    // ✅ 1. Schedule Interview (Admin or Recruiter)
    @PostMapping("/schedule")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECRUITER')")
    public ResponseEntity<Interview> scheduleInterview(@RequestBody Interview interview) {
        Interview saved = interviewService.scheduleInterview(interview);
        return ResponseEntity.ok(saved);
    }

    // ✅ 2. Get Interviews by Candidate ID (Admin only)
    @GetMapping("/candidate/{candidateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Interview>> getInterviewsByCandidateId(@PathVariable Long candidateId) {
        List<Interview> interviews = interviewService.getByCandidateId(candidateId);
        return ResponseEntity.ok(interviews);
    }

    // ✅ 3. Update Interview Status (Admin or Recruiter)
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECRUITER')")
    public ResponseEntity<Interview> updateInterviewStatus(@PathVariable Long id, @RequestBody Interview interviewStatusUpdate) {
        Interview updated = interviewService.updateInterview(id, interviewStatusUpdate);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }
}
