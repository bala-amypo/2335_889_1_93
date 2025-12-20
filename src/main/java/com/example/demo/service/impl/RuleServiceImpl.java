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

@Service
public class RuleServiceImpl implements RuleService {

    private final InteractionRuleRepository ruleRepository;

    public RuleServiceImpl(InteractionRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public InteractionRule addRule(InteractionRule rule) {
        Long idA = rule.getIngredientA().getId();
        Long idB = rule.getIngredientB().getId();

        Optional<InteractionRule> existingRule = ruleRepository.findRuleBetweenIngredients(idA, idB);
        if (existingRule.isPresent()) {
            throw new IllegalArgumentException("Interaction rule already exists between these ingredients");
        }

        if (!List.of("MINOR", "MODERATE", "MAJOR").contains(rule.getSeverity())) {
            throw new IllegalArgumentException("Severity must be MINOR, MODERATE, or MAJOR");
        }

        return ruleRepository.save(rule);
    }

    @Override
    public List<InteractionRule> getAllRules() {
        return ruleRepository.findAll();
    }
}
