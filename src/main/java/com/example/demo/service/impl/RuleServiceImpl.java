package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.InteractionRule;
import com.example.demo.repository.ActiveIngredientRepository;
import com.example.demo.repository.InteractionRuleRepository;
import com.example.demo.service.RuleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RuleServiceImpl implements RuleService {

    private final InteractionRuleRepository ruleRepo;
    private final ActiveIngredientRepository ingredientRepo;

    public RuleServiceImpl(InteractionRuleRepository ruleRepo,
                           ActiveIngredientRepository ingredientRepo) {
        this.ruleRepo = ruleRepo;
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public InteractionRule addRule(InteractionRule rule) {

        if (rule.getIngredientA() == null || rule.getIngredientB() == null) {
            throw new IllegalArgumentException("Both ingredients are required");
        }

        if (rule.getIngredientA().getId().equals(rule.getIngredientB().getId())) {
            throw new IllegalArgumentException("Ingredients must be different");
        }

        Set<String> allowedSeverity = Set.of("MINOR", "MODERATE", "MAJOR");
        if (!allowedSeverity.contains(rule.getSeverity().toUpperCase())) {
            throw new IllegalArgumentException("Severity must be MINOR, MODERATE, or MAJOR");
        }

        // Check if rule already exists
        Optional<InteractionRule> existing = ruleRepo.findRuleBetweenIngredients(
                rule.getIngredientA().getId(), rule.getIngredientB().getId());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Rule already exists for this ingredient pair");
        }

        // Ensure ingredients exist
        ActiveIngredient ingredientA = ingredientRepo.findById(rule.getIngredientA().getId())
                .orElseThrow(() -> new ResourceNotFoundException("IngredientA not found"));
        ActiveIngredient ingredientB = ingredientRepo.findById(rule.getIngredientB().getId())
                .orElseThrow(() -> new ResourceNotFoundException("IngredientB not found"));

        rule.setIngredientA(ingredientA);
        rule.setIngredientB(ingredientB);

        return ruleRepo.save(rule);
    }

    @Override
    public List<InteractionRule> getAllRules() {
        return ruleRepo.findAll();
    }
}
