// package com.example.demo.model;

// import java.util.HashSet;
// import java.util.Set;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "medications")
// public class Medication {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String name;

//     @ManyToMany(cascade = CascadeType.PERSIST)
//     @JoinTable(
//         name = "medication_ingredients",
//         joinColumns = @JoinColumn(name = "medication_id"),
//         inverseJoinColumns = @JoinColumn(name = "ingredient_id")
//     )
//     private Set<ActiveIngredient> ingredients = new HashSet<>();

//     public Medication() {}

//     public Long getId() {
//         return id;
//     }

//     public String getName() {
//         return name;
//     }

//     public Set<ActiveIngredient> getIngredients() {
//         return ingredients;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public void setIngredients(Set<ActiveIngredient> ingredients) {
//         this.ingredients = ingredients;
//     }
// }
package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "medications")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "medication_ingredients",
        joinColumns = @JoinColumn(name = "medication_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    @JsonIgnoreProperties("medications") // ✅ Prevents Swagger infinite loop
    private Set<ActiveIngredient> ingredients = new HashSet<>();

    // ✅ No-arg constructor (required by JPA, Swagger, tests)
    public Medication() {}

    // Optional constructor (safe for business logic)
    public Medication(String name) {
        this.name = name;
    }

    // Getters & Setters
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

    public Set<ActiveIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<ActiveIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
