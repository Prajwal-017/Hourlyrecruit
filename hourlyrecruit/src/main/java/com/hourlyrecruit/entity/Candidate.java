package com.hourlyrecruit.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;

    private String location;

    private String qualification;

    private String resumeUrl;

    private String phoneNumber; // ✅ New field for phone/WhatsApp

    @ElementCollection
    @CollectionTable(name = "candidate_skills", joinColumns = @JoinColumn(name = "candidate_id"))
    @Column(name = "skill")
    private List<String> skills;

    @ManyToMany
    @JoinTable(
        name = "job_applications",
        joinColumns = @JoinColumn(name = "candidate_id"),
        inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private Set<Job> appliedJobs = new HashSet<>();

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public String getPhoneNumber() { // ✅ Getter
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) { // ✅ Setter
        this.phoneNumber = phoneNumber;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public Set<Job> getAppliedJobs() {
        return appliedJobs;
    }

    public void setAppliedJobs(Set<Job> appliedJobs) {
        this.appliedJobs = appliedJobs;
    }
}
