package com.example.demo.repositories;

import com.example.demo.models.InteractionRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InteractionRuleRepository extends JpaRepository<InteractionRule, Long> {
    // REQUIRED BY TEST CASES:
    List<InteractionRule> findByIngredientId(long ingredientId);
    
    // REQUIRED BY TEST CASES: findRuleBetweenIngredients
    @Query("SELECT r FROM InteractionRule r WHERE " +
           "(r.ingredient1.id = :id1 AND r.ingredient2.id = :id2) OR " +
           "(r.ingredient1.id = :id2 AND r.ingredient2.id = :id1)")
    Optional<InteractionRule> findRuleBetweenIngredients(@Param("id1") long id1, @Param("id2") long id2);
}