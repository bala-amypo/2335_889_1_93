package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    @JsonIgnoreProperties("ingredients") // Prevent infinite loop
    private Set<Medication> medications = new HashSet<>();

    // 🔗 Interaction rules where this ingredient is A
    @OneToMany(mappedBy = "ingredientA")
    @JsonIgnoreProperties({"ingredientA", "ingredientB"})
    private Set<InteractionRule> interactionRulesAsA = new HashSet<>();

    // 🔗 Interaction rules where this ingredient is B
    @OneToMany(mappedBy = "ingredientB")
    @JsonIgnoreProperties({"ingredientA", "ingredientB"})
    private Set<InteractionRule> interactionRulesAsB = new HashSet<>();

    // ✅ No-args constructor required by JPA
    public ActiveIngredient() {}

    public ActiveIngredient(String name) {
        this.name = name;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Medication> getMedications() { return medications; }
    public void setMedications(Set<Medication> medications) { this.medications = medications; }

    public Set<InteractionRule> getInteractionRulesAsA() { return interactionRulesAsA; }
    public void setInteractionRulesAsA(Set<InteractionRule> interactionRulesAsA) { this.interactionRulesAsA = interactionRulesAsA; }

    public Set<InteractionRule> getInteractionRulesAsB() { return interactionRulesAsB; }
    public void setInteractionRulesAsB(Set<InteractionRule> interactionRulesAsB) { this.interactionRulesAsB = interactionRulesAsB; }

    // ✅ Equals & hashCode for proper Set behavior
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActiveIngredient)) return false;
        ActiveIngredient that = (ActiveIngredient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Optional: toString for debugging
    @Override
    public String toString() {
        return "ActiveIngredient{id=" + id + ", name='" + name + "'}";
    }
}
