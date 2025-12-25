// package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "active_ingredients")
public class ActiveIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // 🔗 Many-to-Many with Medication
    @ManyToMany(mappedBy = "ingredients")
    @JsonIgnoreProperties("ingredients") // ✅ Prevent Swagger infinite loop
    private Set<Medication> medications = new HashSet<>();

    // 🔗 Interaction rules where this ingredient is A
    @OneToMany(mappedBy = "ingredientA")
    @JsonIgnoreProperties({"ingredientA", "ingredientB"})
    private Set<InteractionRule> interactionRulesAsA = new HashSet<>();

    // 🔗 Interaction rules where this ingredient is B
    @OneToMany(mappedBy = "ingredientB")
    @JsonIgnoreProperties({"ingredientA", "ingredientB"})
    private Set<InteractionRule> interactionRulesAsB = new HashSet<>();

    // ✅ Required by JPA, Swagger, Tests
    public ActiveIngredient() {}

    // Optional constructor
    public ActiveIngredient(String name) {
        this.name = name;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Medication> getMedications() {
        return medications;
    }

    public Set<InteractionRule> getInteractionRulesAsA() {
        return interactionRulesAsA;
    }

    public Set<InteractionRule> getInteractionRulesAsB() {
        return interactionRulesAsB;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMedications(Set<Medication> medications) {
        this.medications = medications;
    }

    public void setInteractionRulesAsA(Set<InteractionRule> interactionRulesAsA) {
        this.interactionRulesAsA = interactionRulesAsA;
    }

    public void setInteractionRulesAsB(Set<InteractionRule> interactionRulesAsB) {
        this.interactionRulesAsB = interactionRulesAsB;
    }
}
