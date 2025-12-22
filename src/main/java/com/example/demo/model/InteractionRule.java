package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "interaction_rules",
    uniqueConstraints = @UniqueConstraint(columnNames = {"ingredientA_id", "ingredientB_id"})
)
public class InteractionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ingredientA_id", nullable = false)
    private ActiveIngredient ingredientA;

    @ManyToOne
    @JoinColumn(name = "ingredientB_id", nullable = false)
    private ActiveIngredient ingredientB;

    @Column(nullable = false)
    private String severity; // MINOR, MODERATE, MAJOR

    @Column(nullable = false)
    private String description;

    public InteractionRule() {}

    public InteractionRule(ActiveIngredient ingredientA, ActiveIngredient ingredientB,
                           String severity, String description) {
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.severity = severity;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ActiveIngredient getIngredientA() { return ingredientA; }
    public void setIngredientA(ActiveIngredient ingredientA) { this.ingredientA = ingredientA; }

    public ActiveIngredient getIngredientB() { return ingredientB; }
    public void setIngredientB(ActiveIngredient ingredientB) { this.ingredientB = ingredientB; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
