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
    
    Optional<Medication> findByName(String name);
    
    boolean existsByName(String name);
    
    List<Medication> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT m FROM Medication m JOIN m.activeIngredients ai WHERE ai.id = :ingredientId")
    List<Medication> findByActiveIngredientId(@Param("ingredientId") Long ingredientId);
    
    Page<Medication> findAll(Pageable pageable);
}