package com.example.demo.repository;

import com.example.demo.model.ActiveIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActiveIngredientRepository extends JpaRepository<ActiveIngredient, Long> {

    // Find ingredient by name
    Optional<ActiveIngredient> findByName(String name);

    // Check if ingredient exists by name
    boolean existsByName(String name);
}

