 package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "interaction_rules")
public class InteractionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ingredient_a_id")
    @JsonIgnoreProperties({"medications", "interactionRulesAsA", "interactionRulesAsB"})
    private ActiveIngredient ingredientA;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ingredient_b_id")
    @JsonIgnoreProperties({"medications", "interactionRulesAsA", "interactionRulesAsB"})
    private ActiveIngredient ingredientB;

    private String severity;
    private String description;

    // ✅ Required by JPA, Swagger, Tests
    public InteractionRule() {}

    // ✅ Used in business logic & tests
    public InteractionRule(
            ActiveIngredient a,
            ActiveIngredient b,
            String severity,
            String description
    ) {
        this.ingredientA = a;
        this.ingredientB = b;
        this.severity = severity;
        this.description = description;
    }

    // Getters & Setters
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
}
