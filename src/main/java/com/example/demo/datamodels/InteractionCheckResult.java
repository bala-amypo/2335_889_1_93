package com.example.project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "interaction_check_results")
public class InteractionCheckResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "medications", columnDefinition = "TEXT", nullable = false)
    private String medications; // comma-separated medication names
    
    @Column(name = "interactions", columnDefinition = "TEXT", nullable = false)
    private String interactions; // JSON summary
    
    @Column(name = "checked_at", nullable = false)
    private LocalDateTime checkedAt;
    
    // PrePersist callback to set checkedAt automatically
    @PrePersist
    protected void onCreate() {
        checkedAt = LocalDateTime.now();
    }
    
    // Constructors
    public InteractionCheckResult() {
    }
    
    public InteractionCheckResult(String medications, String interactions) {
        this.medications = medications;
        this.interactions = interactions;
        this.checkedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
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
    
    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteractionCheckResult that = (InteractionCheckResult) o;
        return Objects.equals(id, that.id) && 
               Objects.equals(checkedAt, that.checkedAt);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, checkedAt);
    }
    
    @Override
    public String toString() {
        return "InteractionCheckResult{" +
                "id=" + id +
                ", medications='" + medications + '\'' +
                ", interactions='" + interactions + '\'' +
                ", checkedAt=" + checkedAt +
                '}';
    }
}