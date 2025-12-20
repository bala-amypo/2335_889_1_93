package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;

@Entity
public class InteractionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ingredient_a_id", nullable = false)
    private ActiveIngredient ingredientA;

    @ManyToOne
    @JoinColumn(name = "ingredient_b_id", nullable = false)
    private ActiveIngredient ingredientB;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;

    @Column(length = 500)
    private String description;

    // Enum for severity
    public enum Severity {
        MINOR,
        MODERATE,
        MAJOR;

        // For JSON deserialization
        @JsonCreator
        public static Severity fromString(String value) {
            return Severity.valueOf(value.trim().toUpperCase());
        }

        // For JSON serialization
        @JsonValue
        public String toValue() {
            return this.name();
        }
    }

    // Getters & setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActiveIngredient getIngredientA() {
        return ingredientA;
    }

    public void setIngredientA(ActiveIngredient ingredientA) {
        this.ingredientA = ingredientA;
    }

    public ActiveIngredient getIngredientB() {
        return ingredientB;
    }

    public void setIngredientB(ActiveIngredient ingredientB) {
        this.ingredientB = ingredientB;
    }

    public Severity getSeverity() {
        return severity;
    }

    // Updated setter to handle string input safely
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
