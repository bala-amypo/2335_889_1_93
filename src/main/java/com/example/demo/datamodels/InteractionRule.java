package com.example.project.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;

@Entity
@Table(name = "interaction_rules", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"ingredient1_id", "ingredient2_id"}))
public class InteractionRule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ingredient1_id", nullable = false, 
                foreignKey = @ForeignKey(name = "fk_interaction_rule_ingredient1"))
    private ActiveIngredient ingredient1;
    
    @ManyToOne
    @JoinColumn(name = "ingredient2_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_interaction_rule_ingredient2"))
    private ActiveIngredient ingredient2;
    
    @Column(nullable = false, length = 20)
    private String severity; // MINOR, MODERATE, MAJOR
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "is_active")
    private Boolean active = true;
    
    // Constructors
    public InteractionRule() {
    }
    
    public InteractionRule(ActiveIngredient ingredient1, ActiveIngredient ingredient2, 
                          String severity, String description) {
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.severity = severity;
        this.description = description;
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
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    // Helper methods
    public boolean isActive() {
        return active != null && active;
    }
    
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
        return "MAJOR".equalsIgnoreCase(severity);
    }
    
    public void normalizeIngredients() {
        if (ingredient1 != null && ingredient2 != null) {
            // Ensure consistent ordering for storage
            Long id1 = ingredient1.getId();
            Long id2 = ingredient2.getId();
            
            if (id1 != null && id2 != null) {
                if (id1 > id2) {
                    // Swap
                    ActiveIngredient temp = ingredient1;
                    ingredient1 = ingredient2;
                    ingredient2 = temp;
                }
            } else {
                // Compare by name if IDs not available
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
        
        // For persisted entities
        if (id != null && that.id != null) {
            return Objects.equals(id, that.id);
        }
        
        // For transient entities, compare by ingredient pair
        return involvesPair(that.ingredient1, that.ingredient2);
    }
    
    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hash(id);
        }
        
        // For transient entities, hash consistently regardless of ingredient order
        if (ingredient1 == null || ingredient2 == null) {
            return Objects.hash(ingredient1, ingredient2);
        }
        
        Long id1 = ingredient1.getId();
        Long id2 = ingredient2.getId();
        
        if (id1 != null && id2 != null) {
            // Use ordered hash for consistency
            return Objects.hash(Math.min(id1, id2), Math.max(id1, id2));
        }
        
        // Use names as fallback
        String name1 = ingredient1.getName();
        String name2 = ingredient2.getName();
        if (name1 == null || name2 == null) {
            return Objects.hash(name1, name2);
        }
        
        // Consistent order for hash
        if (name1.compareTo(name2) < 0) {
            return Objects.hash(name1, name2);
        } else {
            return Objects.hash(name2, name1);
        }
    }
    
    @Override
    public String toString() {
        return "InteractionRule{" +
                "id=" + id +
                ", ingredient1=" + (ingredient1 != null ? ingredient1.getName() : "null") +
                ", ingredient2=" + (ingredient2 != null ? ingredient2.getName() : "null") +
                ", severity='" + severity + '\'' +
                ", active=" + active +
                '}';
    }
}

