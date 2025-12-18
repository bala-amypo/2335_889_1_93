package com.example.demo.repositories;

import com.example.demo.models.Medication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> { 

    
    // REQUIRED BY TEST CASES: Basic finders
    Optional<Medication> findByName(String name);
    boolean existsByName(String name);
    
    // Find by name (case insensitive)
    Optional<Medication> findByNameIgnoreCase(String name);
    List<Medication> findByNameContainingIgnoreCase(String name);
    List<Medication> findByNameContainingIgnoreCase(String name, Sort sort);
    
    // REQUIRED BY TEST CASES: Find by active ingredient
    @Query("SELECT m FROM Medication m JOIN m.activeIngredients ai WHERE ai.id = :ingredientId")
    List<Medication> findByActiveIngredientId(@Param("ingredientId") Long ingredientId);
    
    @Query("SELECT m FROM Medication m JOIN m.activeIngredients ai WHERE ai.name = :ingredientName")
    List<Medication> findByActiveIngredientName(@Param("ingredientName") String ingredientName);
    
    // Case insensitive ingredient name search
    @Query("SELECT m FROM Medication m JOIN m.activeIngredients ai WHERE LOWER(ai.name) = LOWER(:ingredientName)")
    List<Medication> findByActiveIngredientNameIgnoreCase(@Param("ingredientName") String ingredientName);
    
    // Check if medication contains specific ingredient
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Medication m JOIN m.activeIngredients ai WHERE m.id = :medicationId AND ai.id = :ingredientId")
    boolean hasIngredient(@Param("medicationId") Long medicationId, @Param("ingredientId") Long ingredientId);
    
    // Find medications containing multiple ingredients (at least one)
    @Query("SELECT DISTINCT m FROM Medication m JOIN m.activeIngredients ai WHERE ai.id IN :ingredientIds")
    List<Medication> findByActiveIngredientIds(@Param("ingredientIds") List<Long> ingredientIds);
    
    // Find medications containing ALL specified ingredients
    @Query("SELECT m FROM Medication m WHERE " +
           "(SELECT COUNT(DISTINCT ai.id) FROM m.activeIngredients ai WHERE ai.id IN :ingredientIds) = :count")
    List<Medication> findByAllActiveIngredientIds(@Param("ingredientIds") List<Long> ingredientIds, @Param("count") long count);
    
    // Find by multiple names - FIXED: Use Collection
    List<Medication> findByNameIn(Collection<String> names);
    
    // Count methods
    long countByNameContaining(String name);
    long countByNameContainingIgnoreCase(String name);
    
    @Query("SELECT COUNT(DISTINCT m) FROM Medication m JOIN m.activeIngredients ai WHERE ai.id = :ingredientId")
    long countByActiveIngredientId(@Param("ingredientId") Long ingredientId);
    
    // Delete methods
    @Transactional
    @Modifying
    long deleteByName(String name);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Medication m WHERE m.name = :name")
    int deleteByNameCustom(@Param("name") String name);
    
    // Pagination support
    Page<Medication> findAll(Pageable pageable);
    Page<Medication> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    // Find all with sorting
    List<Medication> findAllByOrderByNameAsc();
    List<Medication> findAllByOrderByNameDesc();
    
    // Find by ID list
    List<Medication> findByIdIn(List<Long> ids);
    
    // Check existence with exclusion (for unique validation during updates)
    boolean existsByNameAndIdNot(String name, Long id);
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
    
    // Advanced queries for testing
    
    // Find medications with no ingredients
    @Query("SELECT m FROM Medication m WHERE m.activeIngredients IS EMPTY")
    List<Medication> findMedicationsWithoutIngredients();
    
    // Find medications with specific number of ingredients
    @Query("SELECT m FROM Medication m WHERE SIZE(m.activeIngredients) = :count")
    List<Medication> findByIngredientCount(@Param("count") int count);
    
    // Find medications with ingredient count greater than
    @Query("SELECT m FROM Medication m WHERE SIZE(m.activeIngredients) > :minCount")
    List<Medication> findByIngredientCountGreaterThan(@Param("minCount") int minCount);
    
    // Get medication with all its ingredients (fetch join to avoid N+1 problem)
    @Query("SELECT DISTINCT m FROM Medication m LEFT JOIN FETCH m.activeIngredients WHERE m.id = :id")
    Optional<Medication> findByIdWithIngredients(@Param("id") Long id);
    
    // Get all medications with their ingredients (fetch join)
    @Query("SELECT DISTINCT m FROM Medication m LEFT JOIN FETCH m.activeIngredients")
    List<Medication> findAllWithIngredients();
    
    // Find medications by partial name match
    @Query("SELECT m FROM Medication m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Medication> searchByName(@Param("keyword") String keyword);
    
    // Find medications that share at least one ingredient with given medication
    @Query("SELECT DISTINCT m FROM Medication m " +
           "JOIN m.activeIngredients ai " +
           "WHERE ai IN (SELECT ai2 FROM Medication m2 JOIN m2.activeIngredients ai2 WHERE m2.id = :medicationId) " +
           "AND m.id <> :medicationId")
    List<Medication> findMedicationsWithSharedIngredients(@Param("medicationId") Long medicationId);
    
    // Statistics: Count medications grouped by ingredient count
    @Query("SELECT SIZE(m.activeIngredients) as ingredientCount, COUNT(m) as medicationCount " +
           "FROM Medication m GROUP BY SIZE(m.activeIngredients)")
    List<Object[]> getMedicationStatsByIngredientCount();
    
    // Native query for complex ingredient search (if needed)
    @Query(value = "SELECT DISTINCT m.* FROM medications m " +
           "INNER JOIN medication_ingredients mi ON m.id = mi.medication_id " +
           "INNER JOIN active_ingredients ai ON mi.ingredient_id = ai.id " +
           "WHERE LOWER(ai.name) LIKE LOWER(CONCAT('%', :ingredientName, '%'))", 
           nativeQuery = true)
    List<Medication> searchByIngredientNameNative(@Param("ingredientName") String ingredientName);
}