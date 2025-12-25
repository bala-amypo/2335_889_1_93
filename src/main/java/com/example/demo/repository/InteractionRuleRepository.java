// package com.example.demo.repository;

// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;

// import com.example.demo.model.ActiveIngredient;
// import com.example.demo.model.InteractionRule;

// public interface InteractionRuleRepository
//         extends JpaRepository<InteractionRule, Long> {

//     Optional<InteractionRule> findByIngredientAAndIngredientB(
//             ActiveIngredient ingredientA,
//             ActiveIngredient ingredientB
//     );
// }
package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.InteractionRule;

@Repository
public interface InteractionRuleRepository extends JpaRepository<InteractionRule, Long> {

    // Find a rule for a specific pair of ingredients
    Optional<InteractionRule> findByIngredientAAndIngredientB(
            ActiveIngredient ingredientA,
            ActiveIngredient ingredientB
    );

    // Optional: find a rule regardless of ingredient order
    Optional<InteractionRule> findByIngredientBAndIngredientA(
            ActiveIngredient ingredientA,
            ActiveIngredient ingredientB
    );
}
