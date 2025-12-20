package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class InteractionRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    private Medication medication1;

    @ManyToOne
    private Medication medication2;

    public InteractionRule() {}

    public InteractionRule(String description, Medication medication1, Medication medication2) {
        this.description = description;
        this.medication1 = medication1;
        this.medication2 = medication2;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Medication getMedication1() { return medication1; }
    public void setMedication1(Medication medication1) { this.medication1 = medication1; }

    public Medication getMedication2() { return medication2; }
    public void setMedication2(Medication medication2) { this.medication2 = medication2; }
}
