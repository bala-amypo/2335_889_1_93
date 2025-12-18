package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "interaction_check_results")
public class InteractionCheckResult {
    
    public enum CheckSeverity {
        NONE("No Interactions"),
        MINOR("Minor Interactions"),
        MODERATE("Moderate Interactions"),
        SEVERE("Severe Interactions"),
        CRITICAL("Critical Interactions");
        
        private final String displayName;
        
        CheckSeverity(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "medications", columnDefinition = "TEXT", nullable = false)
    private String medications; // comma-separated medication names
    
    @Column(name = "medication_ids", columnDefinition = "TEXT")
    private String medicationIds; // comma-separated medication IDs
    
    @Column(name = "interactions", columnDefinition = "TEXT", nullable = false)
    private String interactions; // JSON summary
    
    @Column(name = "severity_level", length = 20)
    @Enumerated(EnumType.STRING)
    private CheckSeverity severityLevel;
    
    @Column(name = "checked_at", nullable = false)
    private LocalDateTime checkedAt;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "user_name", length = 100)
    private String userName;
    
    @Column(name = "interaction_count")
    private Integer interactionCount = 0;
    
    @Column(name = "check_duration_ms")
    private Long checkDurationMs; // Time taken for check in milliseconds
    
    // PrePersist callback
    @PrePersist
    protected void onCreate() {
        if (checkedAt == null) {
            checkedAt = LocalDateTime.now();
        }
        if (severityLevel == null) {
            severityLevel = CheckSeverity.NONE;
        }
        if (interactionCount == null) {
            interactionCount = 0;
        }
    }
    
    // Constructors
    public InteractionCheckResult() {
    }
    
    public InteractionCheckResult(String medications, String interactions) {
        this.medications = medications;
        this.interactions = interactions;
        this.checkedAt = LocalDateTime.now();
    }
    
    public InteractionCheckResult(String medications, String medicationIds, 
                                 String interactions, CheckSeverity severityLevel,
                                 Long userId, String userName, Integer interactionCount) {
        this.medications = medications;
        this.medicationIds = medicationIds;
        this.interactions = interactions;
        this.severityLevel = severityLevel;
        this.userId = userId;
        this.userName = userName;
        this.interactionCount = interactionCount;
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
    
    public String getMedicationIds() {
        return medicationIds;
    }
    
    public void setMedicationIds(String medicationIds) {
        this.medicationIds = medicationIds;
    }
    
    public String getInteractions() {
        return interactions;
    }
    
    public void setInteractions(String interactions) {
        this.interactions = interactions;
    }
    
    public CheckSeverity getSeverityLevel() {
        return severityLevel;
    }
    
    public void setSeverityLevel(CheckSeverity severityLevel) {
        this.severityLevel = severityLevel;
    }
    
    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }
    
    public void setCheckedAt(LocalDateTime checkedAt) {
        this.checkedAt = checkedAt;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public Integer getInteractionCount() {
        return interactionCount != null ? interactionCount : 0;
    }
    
    public void setInteractionCount(Integer interactionCount) {
        this.interactionCount = interactionCount;
    }
    
    public Long getCheckDurationMs() {
        return checkDurationMs;
    }
    
    public void setCheckDurationMs(Long checkDurationMs) {
        this.checkDurationMs = checkDurationMs;
    }
    
    // Helper methods
    public boolean hasInteractions() {
        return interactionCount != null && interactionCount > 0;
    }
    
    public boolean isCritical() {
        return severityLevel == CheckSeverity.CRITICAL || severityLevel == CheckSeverity.SEVERE;
    }
    
    public boolean isRecent(int hours) {
        return checkedAt != null && 
               checkedAt.isAfter(LocalDateTime.now().minusHours(hours));
    }
    
    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteractionCheckResult that = (InteractionCheckResult) o;
        
        if (id != null && that.id != null) {
            return Objects.equals(id, that.id);
        }
        
        return Objects.equals(medications, that.medications) &&
               Objects.equals(checkedAt, that.checkedAt);
    }
    
    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hash(id);
        }
        return Objects.hash(medications, checkedAt);
    }
    
    @Override
    public String toString() {
        return "InteractionCheckResult{" +
                "id=" + id +
                ", medications='" + medications + '\'' +
                ", severityLevel=" + severityLevel +
                ", interactionCount=" + interactionCount +
                ", checkedAt=" + checkedAt +
                '}';
    }
}