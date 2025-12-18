package com.example.demo.repositories;

import com.example.demo.models.InteractionRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface InteractionRuleRepository extends JpaRepository<InteractionRule, Long> {
    
    // REQUIRED BY TEST CASES:
    @Query("SELECT r FROM InteractionRule r WHERE r.ingredient1.id = :ingredientId OR r.ingredient2.id = :ingredientId")
    List<InteractionRule> findByIngredientId(@Param("ingredientId") long ingredientId);
    
    // REQUIRED BY TEST CASES: findRuleBetweenIngredients
    @Query("SELECT r FROM InteractionRule r WHERE " +
           "(r.ingredient1.id = :id1 AND r.ingredient2.id = :id2) OR " +
           "(r.ingredient1.id = :id2 AND r.ingredient2.id = :id1)")
    Optional<InteractionRule> findRuleBetweenIngredients(@Param("id1") long id1, @Param("id2") long id2);
    
    // Find rules by severity
    List<InteractionRule> findBySeverity(String severity);
    List<InteractionRule> findBySeverityIn(List<String> severities);
    
    // Find active/inactive rules
    List<InteractionRule> findByActive(boolean active);
    List<InteractionRule> findByActiveTrue();
    List<InteractionRule> findByActiveFalse();
    
    // Find rules involving multiple ingredients
    @Query("SELECT r FROM InteractionRule r WHERE " +
           "r.ingredient1.id IN :ingredientIds OR r.ingredient2.id IN :ingredientIds")
    List<InteractionRule> findByIngredientIds(@Param("ingredientIds") List<Long> ingredientIds);
    
    @Query("SELECT r FROM InteractionRule r WHERE " +
           "(r.ingredient1.id IN :ingredientIds AND r.ingredient2.id IN :ingredientIds)")
    List<InteractionRule> findRulesBetweenIngredients(@Param("ingredientIds") List<Long> ingredientIds);
    
    // Find critical rules (MAJOR severity)
    @Query("SELECT r FROM InteractionRule r WHERE r.severity = 'MAJOR' OR r.severity = 'CONTRAINDICATED'")
    List<InteractionRule> findCriticalRules();
    
    // Check if rule exists between ingredients
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM InteractionRule r WHERE " +
           "(r.ingredient1.id = :id1 AND r.ingredient2.id = :id2) OR " +
           "(r.ingredient1.id = :id2 AND r.ingredient2.id = :id1)")
    boolean existsBetweenIngredients(@Param("id1") long id1, @Param("id2") long id2);
    
    // Find rules with ingredient names
    @Query("SELECT r FROM InteractionRule r WHERE " +
           "LOWER(r.ingredient1.name) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(r.ingredient2.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<InteractionRule> findByIngredientNameContaining(@Param("name") String name);
    
    // Find rules for ingredient pair regardless of order
    default Optional<InteractionRule> findByIngredientPair(Long ingredientId1, Long ingredientId2) {
        return findRuleBetweenIngredients(ingredientId1, ingredientId2);
    }
    
    // Count methods
    long countBySeverity(String severity);
    
    @Query("SELECT COUNT(r) FROM InteractionRule r WHERE r.ingredient1.id = :id OR r.ingredient2.id = :id")
    long countByIngredientId(@Param("id") long id);
    
    // Delete methods - FIXED: Return int for affected rows count
    @Transactional
    @Modifying
    @Query("DELETE FROM InteractionRule r WHERE r.ingredient1.id = :id OR r.ingredient2.id = :id")
    int deleteByIngredientId(@Param("id") long id);
    
    // Find all rules ordered by severity (most severe first)
    @Query("SELECT r FROM InteractionRule r ORDER BY " +
           "CASE r.severity " +
           "WHEN 'CONTRAINDICATED' THEN 1 " +
           "WHEN 'MAJOR' THEN 2 " +
           "WHEN 'MODERATE' THEN 3 " +
           "WHEN 'MINOR' THEN 4 " +
           "ELSE 5 END")
    List<InteractionRule> findAllOrderBySeverity();
    
    // Find rules with pagination - ADDED: Pageable parameter
    @Query("SELECT r FROM InteractionRule r WHERE r.active = true")
    Page<InteractionRule> findActiveRules(Pageable pageable);
    
    // Alternative: List version without pagination
    @Query("SELECT r FROM InteractionRule r WHERE r.active = true")
    List<InteractionRule> findAllActiveRules();
    
    // Bulk operations
    @Query("SELECT r FROM InteractionRule r WHERE r.id IN :ids")
    List<InteractionRule> findByIds(@Param("ids") List<Long> ids);
    
    // Alternative: Use built-in method
    List<InteractionRule> findByIdIn(List<Long> ids);
    
    // Find rules by ingredient name (exact match)
    @Query("SELECT r FROM InteractionRule r WHERE " +
           "r.ingredient1.name = :name OR r.ingredient2.name = :name")
    List<InteractionRule> findByIngredientName(@Param("name") String name);
    
    // Find rules with description containing keyword
    List<InteractionRule> findByDescriptionContainingIgnoreCase(String keyword);
    
    // Statistics
    @Query("SELECT r.severity, COUNT(r) FROM InteractionRule r GROUP BY r.severity")
    List<Object[]> countBySeverityGroup();
}