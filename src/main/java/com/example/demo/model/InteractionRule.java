package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class InteractionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Ingredient ingredientA;

    @Embedded
    private Ingredient ingredientB;

    private String severity;
    private String description;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Ingredient getIngredientA() { return ingredientA; }
    public void setIngredientA(Ingredient ingredientA) { this.ingredientA = ingredientA; }

    public Ingredient getIngredientB() { return ingredientB; }
    public void setIngredientB(Ingredient ingredientB) { this.ingredientB = ingredientB; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

@Embeddable
class Ingredient {
    private Long id;
    private String name;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
