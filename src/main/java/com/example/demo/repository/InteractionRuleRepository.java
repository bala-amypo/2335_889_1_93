package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.InteractionRule;

public interface InteractionRuleRepository
        extends JpaRepository<InteractionRule, Long> {

    Optional<InteractionRule> findByIngredientAAndIngredientB(
            ActiveIngredient ingredientA,
            ActiveIngredient ingredientB
    );
}
