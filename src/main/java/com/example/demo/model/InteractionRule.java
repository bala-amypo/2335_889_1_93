package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class InteractionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ActiveIngredient ingredientA;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ActiveIngredient ingredientB;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;

    @Column(length = 500)
    private String description;

    public enum Severity { MINOR, MODERATE, MAJOR }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ActiveIngredient getIngredientA() { return ingredientA; }
    public void setIngredientA(ActiveIngredient ingredientA) { this.ingredientA = ingredientA; }

    public ActiveIngredient getIngredientB() { return ingredientB; }
    public void setIngredientB(ActiveIngredient ingredientB) { this.ingredientB = ingredientB; }

    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
