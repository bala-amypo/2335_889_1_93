package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "medication_ingredient",
        joinColumns = @JoinColumn(name = "medication_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<ActiveIngredient> ingredients;

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<ActiveIngredient> getIngredients() { return ingredients; }
    public void setIngredients(List<ActiveIngredient> ingredients) { this.ingredients = ingredients; }
}
