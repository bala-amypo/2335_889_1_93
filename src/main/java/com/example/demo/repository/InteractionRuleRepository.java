

package com.example.demo.repository;

import com.example.demo.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;



public interface InteractionRuleRepository extends JpaRepository<InteractionRule, Long> {

    @Query("""
    SELECT r FROM InteractionRule r
    WHERE (r.ingredientA.id = :a AND r.ingredientB.id = :b)
       OR (r.ingredientA.id = :b AND r.ingredientB.id = :a)
    """)
    Optional<InteractionRule> findRuleBetweenIngredients(Long a, Long b);
}
