package com.example.project.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
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
                                 foreignKey = @ForeignKey(name = "fk_medication_ingredient_med")),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id",
                                       foreignKey = @ForeignKey(name = "fk_medication_ingredient_ing"))
    )
    private Set<ActiveIngredient> ingredients = new HashSet<>();
    
    // Constructors
    public Medication() {
        // Default constructor for JPA
    }
    
    public Medication(String name) {
        this.name = name;
    }
    
    public Medication(String name, Set<ActiveIngredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients != null ? ingredients : new HashSet<>();
    }
    
    public Medication(String name, String brandName, String strength, String dosageForm) {
        this.name = name;
        this.brandName = brandName;
        this.strength = strength;
        this.dosageForm = dosageForm;
        this.active = true;
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
    
    public String getBrandName() {
        return brandName;
    }
    
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    
    public String getStrength() {
        return strength;
    }
    
    public void setStrength(String strength) {
        this.strength = strength;
    }
    
    public String getDosageForm() {
        return dosageForm;
    }
    
    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public Set<ActiveIngredient> getIngredients() {
        return ingredients;
    }
    
    public void setIngredients(Set<ActiveIngredient> ingredients) {
        this.ingredients = ingredients != null ? ingredients : new HashSet<>();
    }
    
    // Helper methods for managing ingredients
    public void addIngredient(ActiveIngredient ingredient) {
        if (ingredient != null) {
            this.ingredients.add(ingredient);
        }
    }
    
    public void removeIngredient(ActiveIngredient ingredient) {
        if (ingredient != null) {
            this.ingredients.remove(ingredient);
        }
    }
    
    public boolean containsIngredient(ActiveIngredient ingredient) {
        return ingredient != null && this.ingredients.contains(ingredient);
    }
    
    public boolean containsIngredientByName(String ingredientName) {
        if (ingredientName == null || this.ingredients.isEmpty()) {
            return false;
        }
        return this.ingredients.stream()
                .anyMatch(ing -> ingredientName.equalsIgnoreCase(ing.getName()));
    }
    
    public int getIngredientCount() {
        return this.ingredients.size();
    }
    
    public boolean hasIngredients() {
        return !this.ingredients.isEmpty();
    }
    
    public Set<String> getIngredientNames() {
        Set<String> names = new HashSet<>();
        for (ActiveIngredient ingredient : ingredients) {
            names.add(ingredient.getName());
        }
        return names;
    }
    
    // Business logic methods
    public String getDisplayName() {
        if (brandName != null && !brandName.isEmpty()) {
            return name + " (" + brandName + ")";
        }
        return name;
    }
    
    public String getFullDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        
        if (brandName != null && !brandName.isEmpty()) {
            sb.append(" (").append(brandName).append(")");
        }
        
        if (strength != null && !strength.isEmpty()) {
            sb.append(" ").append(strength);
        }
        
        if (dosageForm != null && !dosageForm.isEmpty()) {
            sb.append(" ").append(dosageForm);
        }
        
        return sb.toString();
    }
    
    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medication that = (Medication) o;
        
        // For persisted entities
        if (id != null && that.id != null) {
            return Objects.equals(id, that.id);
        }
        
        // For transient entities, compare by name (assuming name is unique)
        return Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        // For persisted entities
        if (id != null) {
            return Objects.hash(id);
        }
        
        // For transient entities
        return Objects.hash(name);
    }
    
    @Override
    public String toString() {
        return "Medication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brandName='" + brandName + '\'' +
                ", ingredientCount=" + ingredients.size() +
                ", active=" + active +
                '}';
    }
}