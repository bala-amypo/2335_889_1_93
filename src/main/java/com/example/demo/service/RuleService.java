package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.InteractionRule;

public interface RuleService {

    InteractionRule addRule(InteractionRule rule);

    Optional<InteractionRule> findRule(Long ingredientAId, Long ingredientBId);

    List<InteractionRule> getAllRules();
}
