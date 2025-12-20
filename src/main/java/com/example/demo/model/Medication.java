package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "medications")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "medication_ingredient",
        joinColumns = @JoinColumn(name = "medication_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<ActiveIngredient> ingredients;

    public Medication() {}
    public Medication(String name) { this.name = name; }

    // getters and setters
}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<ActiveIngredient> getIngredients() { return ingredients; }
    public void setIngredients(Set<ActiveIngredient> ingredients) { this.ingredients = ingredients; }
}
