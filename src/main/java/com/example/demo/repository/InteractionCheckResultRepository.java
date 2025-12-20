
package com.example.demo.repository;

import com.example.demo.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

public interface ActiveIngredientRepository extends JpaRepository<ActiveIngredient, Long> {
    boolean existsByName(String name);
}

public interface MedicationRepository extends JpaRepository<Medication, Long> {}

public interface InteractionCheckResultRepository
        extends JpaRepository<InteractionCheckResult, Long> {}

public interface InteractionRuleRepository extends JpaRepository<InteractionRule, Long> {

    @Query("""
    SELECT r FROM InteractionRule r
    WHERE (r.ingredientA.id = :a AND r.ingredientB.id = :b)
       OR (r.ingredientA.id = :b AND r.ingredientB.id = :a)
    """)
    Optional<InteractionRule> findRuleBetweenIngredients(Long a, Long b);
}
