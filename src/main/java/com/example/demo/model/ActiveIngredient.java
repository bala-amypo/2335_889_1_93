package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(
    name = "active_ingredients",
    uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class ActiveIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ingredient name must not be blank")
    @Size(max = 100, message = "Ingredient name must not exceed 100 characters")
    @Column(nullable = false, unique = true)
    private String name;

    public ActiveIngredient() {}

    public ActiveIngredient(String name) {
        this.name = name;
    }

    // getters & setters
}
