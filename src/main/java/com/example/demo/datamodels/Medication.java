package com.example.project.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "medications")
public class Medication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 255)
    private String name;
    
    @Column(name = "brand_name", length = 255)
    private String brandName;
    
    @Column(name = "strength", length = 100)
    private String strength;
    
    @Column(name = "dosage_form", length = 100)
    private String dosageForm;
    
    @Column(name = "is_active", nullable = false)
    private boolean active = true;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "medication_ingredients",
        joinColumns = @JoinColumn(name = "medication_id", 
                                 foreignKey = @jakarta.persistence.ForeignKey(name = "fk_medication_ingredient_med")),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id",
                                       foreignKey = @jakarta.persistence.ForeignKey(name = "fk_medication_ingredient_ing"))
    )
    private Set<ActiveIngredient> ingredients = new HashSet<>();
    
    // ... rest of the class remains the same ...
}