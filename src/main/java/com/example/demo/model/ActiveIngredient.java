package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "active_ingredients",
       uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class ActiveIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ingredient name cannot be empty")
    @Size(max = 100, message = "Ingredient name must be less than 100 characters")
    @Column(nullable = false, unique = true)
    private String name;

    public ActiveIngredient() {}

    public ActiveIngredient(String name) {
        this.name = name;
    }

    // getters and setters
}
