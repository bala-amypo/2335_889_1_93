package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class InteractionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ingredientA;
    private String ingredientB;
    private String description;
    private String severity;

    public InteractionRule() {}

    public InteractionRule(String ingredientA, String ingredientB, String description, String severity) {
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.description = description;
        this.severity = severity;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIngredientA() { return ingredientA; }
    public void setIngredientA(String ingredientA) { this.ingredientA = ingredientA; }
    public String getIngredientB() { return ingredientB; }
    public void setIngredientB(String ingredientB) { this.ingredientB = ingredientB; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
}
