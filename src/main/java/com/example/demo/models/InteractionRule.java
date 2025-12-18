package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "interaction_rules", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"ingredient1_id", "ingredient2_id"}))
public class InteractionRule {
    
    public enum Severity {
        MINOR("Minor"),
        MODERATE("Moderate"), 
        MAJOR("Major"),
        CONTRAINDICATED("Contraindicated");
        
        private final String displayName;
        
        Severity(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public boolean isCritical() {
            return this == MAJOR || this == CONTRAINDICATED;
        }
        
        public int getLevel() {
            return switch (this) {
                case MINOR -> 1;
                case MODERATE -> 2;
                case MAJOR -> 3;
                case CONTRAINDICATED -> 4;
            };
        }
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ingredient1_id", nullable = false)
    private ActiveIngredient ingredient1;
    
    @ManyToOne
    @JoinColumn(name = "ingredient2_id", nullable = false)
    private ActiveIngredient ingredient2;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Severity severity;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "recommendation", columnDefinition = "TEXT")
    private String recommendation;
    
    @Column(name = "mechanism", length = 255)
    private String mechanism;
    
    @Column(name = "is_active", nullable = false)
    private boolean active = true;
    
    @Column(name = "created_at", updatable = false)
    private java.time.LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = createdAt;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }
    
    // Constructors
    public InteractionRule() {
    }
    
    public InteractionRule(ActiveIngredient ingredient1, ActiveIngredient ingredient2, 
                          Severity severity, String description) {
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.severity = severity;
        this.description = description;
        this.active = true;
    }
    
    public InteractionRule(ActiveIngredient ingredient1, ActiveIngredient ingredient2,
                          Severity severity, String description, String recommendation, 
                          String mechanism) {
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.severity = severity;
        this.description = description;
        this.recommendation = recommendation;
        this.mechanism = mechanism;
        this.active = true;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public ActiveIngredient getIngredient1() {
        return ingredient1;
    }
    
    public void setIngredient1(ActiveIngredient ingredient1) {
        this.ingredient1 = ingredient1;
    }
    
    public ActiveIngredient getIngredient2() {
        return ingredient2;
    }
    
    public void setIngredient2(ActiveIngredient ingredient2) {
        this.ingredient2 = ingredient2;
    }
    
    public Severity getSeverity() {
        return severity;
    }
    
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getRecommendation() {
        return recommendation;
    }
    
    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
    
    public String getMechanism() {
        return mechanism;
    }
    
    public void setMechanism(String mechanism) {
        this.mechanism = mechanism;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public java.time.LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(java.time.LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Helper methods
    public boolean involvesPair(ActiveIngredient ing1, ActiveIngredient ing2) {
        if (ingredient1 == null || ingredient2 == null || ing1 == null || ing2 == null) {
            return false;
        }
        return (ingredient1.equals(ing1) && ingredient2.equals(ing2)) ||
               (ingredient1.equals(ing2) && ingredient2.equals(ing1));
    }
    
    public boolean involvesIngredient(ActiveIngredient ingredient) {
        if (ingredient1 == null || ingredient2 == null || ingredient == null) {
            return false;
        }
        return ingredient1.equals(ingredient) || ingredient2.equals(ingredient);
    }
    
    public ActiveIngredient getOtherIngredient(ActiveIngredient ingredient) {
        if (ingredient == null) {
            return null;
        }
        if (ingredient1.equals(ingredient)) {
            return ingredient2;
        } else if (ingredient2.equals(ingredient)) {
            return ingredient1;
        }
        return null;
    }
    
    public boolean isCritical() {
        return severity != null && severity.isCritical();
    }
    
    public void normalizeIngredients() {
        if (ingredient1 != null && ingredient2 != null) {
            Long id1 = ingredient1.getId();
            Long id2 = ingredient2.getId();
            
            if (id1 != null && id2 != null) {
                if (id1 > id2) {
                    // Swap ingredients
                    ActiveIngredient temp = ingredient1;
                    ingredient1 = ingredient2;
                    ingredient2 = temp;
                }
            } else {
                String name1 = ingredient1.getName();
                String name2 = ingredient2.getName();
                if (name1 != null && name2 != null && name1.compareTo(name2) > 0) {
                    ActiveIngredient temp = ingredient1;
                    ingredient1 = ingredient2;
                    ingredient2 = temp;
                }
            }
        }
    }
    
    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteractionRule that = (InteractionRule) o;
        
        if (id != null && that.id != null) {
            return java.util.Objects.equals(id, that.id);
        }
        
        return involvesPair(that.ingredient1, that.ingredient2);
    }
    
    @Override
    public int hashCode() {
        if (id != null) {
            return java.util.Objects.hash(id);
        }
        
        if (ingredient1 == null || ingredient2 == null) {
            return java.util.Objects.hash(ingredient1, ingredient2);
        }
        
        Long id1 = ingredient1.getId();
        Long id2 = ingredient2.getId();
        
        if (id1 != null && id2 != null) {
            return java.util.Objects.hash(Math.min(id1, id2), Math.max(id1, id2));
        }
        
        String name1 = ingredient1.getName();
        String name2 = ingredient2.getName();
        if (name1 == null || name2 == null) {
            return java.util.Objects.hash(name1, name2);
        }
        
        if (name1.compareTo(name2) < 0) {
            return java.util.Objects.hash(name1, name2);
        } else {
            return java.util.Objects.hash(name2, name1);
        }
    }
    
    @Override
    public String toString() {
        return "InteractionRule{" +
                "id=" + id +
                ", ingredient1=" + (ingredient1 != null ? ingredient1.getName() : "null") +
                ", ingredient2=" + (ingredient2 != null ? ingredient2.getName() : "null") +
                ", severity=" + severity +
                ", active=" + active +
                ", createdAt=" + createdAt +
                '}';
    }
}