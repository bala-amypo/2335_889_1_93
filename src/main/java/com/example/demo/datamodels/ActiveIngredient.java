package com.example.project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "active_ingredients")
public class ActiveIngredient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 255)
    private String name;
    
    // Constructors
    public ActiveIngredient() {
        // Default constructor for JPA
    }
    
    public ActiveIngredient(String name) {
        this.name = name;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActiveIngredient that = (ActiveIngredient) o;
        
        // For persisted entities, compare by ID
        if (id != null && that.id != null) {
            return Objects.equals(id, that.id);
        }
        
        // For transient entities, compare by name
        return Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        // For persisted entities, hash by ID
        if (id != null) {
            return Objects.hash(id);
        }
        
        // For transient entities, hash by name
        return Objects.hash(name);
    }
    
    @Override
    public String toString() {
        return "ActiveIngredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}