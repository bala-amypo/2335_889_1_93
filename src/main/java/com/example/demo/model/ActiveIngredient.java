package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "active_ingredients",
    uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class ActiveIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public ActiveIngredient() {
    }

    public ActiveIngredient(String name) {
        this.name = name != null ? name.trim() : null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name != null ? name.trim() : null; }
}
