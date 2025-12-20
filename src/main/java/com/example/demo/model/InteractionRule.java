package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interaction_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InteractionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ingredient_a_id")
    private ActiveIngredient ingredientA;

    @ManyToOne
    @JoinColumn(name = "ingredient_b_id")
    private ActiveIngredient ingredientB;

    private String severity; // e.g., "High", "Medium", "Low"
    private String description;
}
