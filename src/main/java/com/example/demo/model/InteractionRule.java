package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "interaction_rules")
public class InteractionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ingredientA;

    @Column(nullable = false)
    private String ingredientB;

    private String severity;
    private String description;

    // Constructors
    public InteractionRule() {}
    public InteractionRule(String ingredientA, String ingredientB, String severity, String description) {
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.severity = severity;
        this.description = description;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIngredientA() { return ingredientA; }
    public void setIngredientA(String ingredientA) { this.ingredientA = ingredientA; }

    public String getIngredientB() { return ingredientB; }
    public void setIngredientB(String ingredientB) { this.ingredientB = ingredientB; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
