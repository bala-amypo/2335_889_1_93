package com.example.project.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "interaction_rules", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"ingredient1_id", "ingredient2_id"}))
public class InteractionRule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ingredient1_id", nullable = false)
    private ActiveIngredient ingredient1;
    
    @ManyToOne
    @JoinColumn(name = "ingredient2_id", nullable = false)
    private ActiveIngredient ingredient2;
    
    @Column(nullable = false)
    private String severity; // MINOR, MODERATE, MAJOR
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    // Constructors
    public InteractionRule() {
    }
    
    public InteractionRule(ActiveIngredient ingredient1, ActiveIngredient ingredient2, 
                          String severity, String description) {
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.severity = severity;
        this.description = description;
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
    
    public String getSeverity() {
        return severity;
    }
    
    public void setSeverity(String severity) {
        this.severity = severity;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    // Helper method to check if rule involves a specific ingredient pair
    public boolean involvesPair(ActiveIngredient ing1, ActiveIngredient ing2) {
        return (ingredient1.equals(ing1) && ingredient2.equals(ing2)) ||
               (ingredient1.equals(ing2) && ingredient2.equals(ing1));
    }
    
    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteractionRule that = (InteractionRule) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "InteractionRule{" +
                "id=" + id +
                ", ingredient1=" + (ingredient1 != null ? ingredient1.getName() : "null") +
                ", ingredient2=" + (ingredient2 != null ? ingredient2.getName() : "null") +
                ", severity='" + severity + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}