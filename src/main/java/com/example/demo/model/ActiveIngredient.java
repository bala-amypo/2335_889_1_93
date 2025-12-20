package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "active_ingredients", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "ingredients")
    private Set<Medication> medications = new HashSet<>();

    public ActiveIngredient(String name) {
        this.name = name;
    }
}
