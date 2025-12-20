package com.example.demo.service.impl;

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
    private final ActiveIngredientRepository ingredientRepository;

    public RuleServiceImpl(InteractionRuleRepository ruleRepository, ActiveIngredientRepository ingredientRepository) {
        this.ruleRepository = ruleRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public InteractionRule addRule(InteractionRule rule) {
        // Ensure both ingredients exist
        ActiveIngredient a = ingredientRepository.findById(rule.getIngredientA().getId())
                .orElseThrow(() -> new IllegalArgumentException("Ingredient A not found"));
        ActiveIngredient b = ingredientRepository.findById(rule.getIngredientB().getId())
                .orElseThrow(() -> new IllegalArgumentException("Ingredient B not found"));

        rule.setIngredientA(a);
        rule.setIngredientB(b);

        // Check severity
        if (!"MINOR".equalsIgnoreCase(rule.getSeverity()) &&
            !"MODERATE".equalsIgnoreCase(rule.getSeverity()) &&
            !"MAJOR".equalsIgnoreCase(rule.getSeverity())) {
            throw new IllegalArgumentException("Severity must be MINOR, MODERATE, or MAJOR");
        }

        // Check uniqueness (A-B or B-A)
        Optional<InteractionRule> existing = ruleRepository.findAll().stream()
                .filter(r -> (r.getIngredientA().getId().equals(a.getId()) && r.getIngredientB().getId().equals(b.getId()))
                          || (r.getIngredientA().getId().equals(b.getId()) && r.getIngredientB().getId().equals(a.getId())))
                .findAny();

        if (existing.isPresent()) throw new IllegalArgumentException("Rule already exists for this ingredient pair");

        return ruleRepository.save(rule);
    }

    @Override
    public List<InteractionRule> getAllRules() {
        return ruleRepository.findAll();
    }
}
