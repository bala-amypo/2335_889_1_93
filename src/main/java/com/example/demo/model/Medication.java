package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private ActiveIngredient activeIngredient;

    public Medication() {}
    public Medication(String name, ActiveIngredient activeIngredient) {
        this.name = name;
        this.activeIngredient = activeIngredient;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public ActiveIngredient getActiveIngredient() { return activeIngredient; }
    public void setActiveIngredient(ActiveIngredient activeIngredient) { this.activeIngredient = activeIngredient; }
}
