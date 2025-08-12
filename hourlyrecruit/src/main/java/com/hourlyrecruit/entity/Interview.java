package com.hourlyrecruit.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interviews")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mode;

    private String status;

    private LocalDateTime scheduledTime;

    private LocalDateTime dateTime;

    @ManyToOne
    private Candidate candidate;

    @ManyToOne
    private Job job;

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public String getMode() {
        return mode;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Interview [id=" + id + ", candidate=" + (candidate != null ? candidate.getId() : null)
                + ", job=" + (job != null ? job.getId() : null)
                + ", mode=" + mode + ", status=" + status
                + ", scheduledTime=" + scheduledTime + ", dateTime=" + dateTime + "]";
    }
}
