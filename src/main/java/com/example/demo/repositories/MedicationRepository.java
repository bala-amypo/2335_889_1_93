package com.example.demo.repositories;

import com.example.demo.models.Medication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    
    // Required for CatalogService
    Optional<Medication> findByName(String name);
    boolean existsByName(String name);
    
    // Find by name (case insensitive)
    Optional<Medication> findByNameIgnoreCase(String name);
    List<Medication> findByNameContainingIgnoreCase(String name);
    
    // Required for InteractionService
    @Query("SELECT m FROM Medication m JOIN m.activeIngredients ai WHERE ai.id = :ingredientId")
    List<Medication> findByActiveIngredientId(@Param("ingredientId") Long ingredientId);
    
    @Query("SELECT m FROM Medication m JOIN m.activeIngredients ai WHERE ai.name = :ingredientName")
    List<Medication> findByActiveIngredientName(@Param("ingredientName") String ingredientName);
    
    // Check if medication contains specific ingredient
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Medication m JOIN m.activeIngredients ai WHERE m.id = :medicationId AND ai.id = :ingredientId")
    boolean hasIngredient(@Param("medicationId") Long medicationId, @Param("ingredientId") Long ingredientId);
    
    // Find medications containing multiple ingredients
    @Query("SELECT DISTINCT m FROM Medication m JOIN m.activeIngredients ai WHERE ai.id IN :ingredientIds")
    List<Medication> findByActiveIngredientIds(@Param("ingredientIds") List<Long> ingredientIds);
    
    // Pagination support
    Page<Medication> findAll(Pageable pageable);
    Page<Medication> findByNameContainingIgnoreCase(String name, Pageable pageable);
}