 package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "interaction_check_results")
public class InteractionCheckResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Comma-separated or JSON string of medication names
    @Column(nullable = false)
    private String medications;

    // Detailed interaction results (can be large)
    @Column(columnDefinition = "TEXT")
    private String interactions;

    @Column(nullable = false)
    private LocalDateTime checkedAt;

    // ✅ Required by JPA, Swagger, Tests
    public InteractionCheckResult() {
        this.checkedAt = LocalDateTime.now();
    }

    // ✅ Used in services & tests
    public InteractionCheckResult(String medications, String interactions) {
        this.medications = medications;
        this.interactions = interactions;
        this.checkedAt = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getInteractions() {
        return interactions;
    }

    public void setInteractions(String interactions) {
        this.interactions = interactions;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(LocalDateTime checkedAt) {
        this.checkedAt = checkedAt;
    }
}
