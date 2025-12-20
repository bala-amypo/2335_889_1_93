package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "interaction_rules", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ingredient_a_id", "ingredient_b_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(nullable = false)
    private String severity; // MINOR, MODERATE, MAJOR

    private String description;

    public InteractionRule(ActiveIngredient ingredientA, ActiveIngredient ingredientB, String severity, String description) {
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.severity = severity;
        this.description = description;
    }
}
