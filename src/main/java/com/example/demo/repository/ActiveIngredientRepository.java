package com.example.demo.repository;

import com.example.demo.model.ActiveIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for ActiveIngredient entities.
 * Extends JpaRepository to provide CRUD operations and query capabilities.
 */
@Repository
public interface ActiveIngredientRepository extends JpaRepository<ActiveIngredient, Long> {
    
    // Example of a custom query method:
    // Optional<ActiveIngredient> findByName(String name);

}
