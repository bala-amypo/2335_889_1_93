 package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.InteractionRule;
import com.example.demo.repository.ActiveIngredientRepository;
import com.example.demo.repository.InteractionRuleRepository;
import com.example.demo.service.RuleService;

@Service
@Transactional
public class RuleServiceImpl implements RuleService {

    private final InteractionRuleRepository ruleRepository;
    private final ActiveIngredientRepository ingredientRepository;

    public RuleServiceImpl(
            InteractionRuleRepository ruleRepository,
            ActiveIngredientRepository ingredientRepository) {
        this.ruleRepository = ruleRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public InteractionRule addRule(InteractionRule rule) {
        rule.setId(null); // force INSERT
        return ruleRepository.save(rule);
    }

    @Override
    public Optional<InteractionRule> findRule(
            Long ingredientAId,
            Long ingredientBId) {

        ActiveIngredient a = ingredientRepository.findById(ingredientAId)
                .orElseThrow(() -> new RuntimeException("Ingredient A not found"));

        ActiveIngredient b = ingredientRepository.findById(ingredientBId)
                .orElseThrow(() -> new RuntimeException("Ingredient B not found"));

        return ruleRepository.findByIngredientAAndIngredientB(a, b)
                .or(() -> ruleRepository.findByIngredientAAndIngredientB(b, a));
    }

    @Override
    public List<InteractionRule> getAllRules() {
        return ruleRepository.findAll();
    }
}
