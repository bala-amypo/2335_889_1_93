package com.example.demo.controller;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.InteractionRule;
import com.example.demo.repository.ActiveIngredientRepository;
import com.example.demo.repository.InteractionRuleRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rules")
public class RuleController {

    private final InteractionRuleRepository ruleRepository;
    private final ActiveIngredientRepository ingredientRepository;

    public RuleController(InteractionRuleRepository ruleRepository,
                          ActiveIngredientRepository ingredientRepository) {
        this.ruleRepository = ruleRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @PostMapping
    public ResponseEntity<InteractionRule> createRule(@Valid @RequestBody InteractionRule rule) {

        // Handle ingredient A
        if (rule.getIngredientA().getId() != null) {
            rule.setIngredientA(
                ingredientRepository.findById(rule.getIngredientA().getId())
                    .orElseGet(() -> ingredientRepository.save(rule.getIngredientA()))
            );
        } else {
            rule.setIngredientA(ingredientRepository.save(rule.getIngredientA()));
        }

        // Handle ingredient B
        if (rule.getIngredientB().getId() != null) {
            rule.setIngredientB(
                ingredientRepository.findById(rule.getIngredientB().getId())
                    .orElseGet(() -> ingredientRepository.save(rule.getIngredientB()))
            );
        } else {
            rule.setIngredientB(ingredientRepository.save(rule.getIngredientB()));
        }

        // Save rule
        InteractionRule savedRule = ruleRepository.save(rule);
        return ResponseEntity.ok(savedRule);
    }

    @GetMapping
    public ResponseEntity<?> getAllRules() {
        return ResponseEntity.ok(ruleRepository.findAll());
    }
}
