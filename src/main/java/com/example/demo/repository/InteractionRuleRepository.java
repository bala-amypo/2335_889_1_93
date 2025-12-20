package com.example.demo.repository;

import com.example.demo.entity.InteractionRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface InteractionRuleRepository extends JpaRepository<InteractionRule, Long> {
    
    List<InteractionRule> findByIngredientId(Long ingredientId);

    @Query("SELECT r FROM InteractionRule r WHERE " +
           "(r.ingredient1.id = :id1 AND r.ingredient2.id = :id2) OR " +
           "(r.ingredient1.id = :id2 AND r.ingredient2.id = :id1)")
    Optional<InteractionRule> findRuleBetweenIngredients(@Param("id1") Long id1, @Param("id2") Long id2);
}
