package com.example.demo.repository;

import com.example.demo.model.ActiveIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveIngredientRepository extends JpaRepository<ActiveIngredient, Long> {

    // Checks if an ActiveIngredient exists by its name
    boolean existsByName(String name);
}
