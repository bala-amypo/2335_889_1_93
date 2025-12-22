package com.example.demo.controller;

import com.example.demo.dto.InteractionRuleRequest;
import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.InteractionRule;
import com.example.demo.service.RuleService;
import com.example.demo.repository.ActiveIngredientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
public class RuleController {

    private final RuleService ruleService;
    private final ActiveIngredientRepository ingredientRepo;

    public RuleController(RuleService ruleService, ActiveIngredientRepository ingredientRepo) {
        this.ruleService = ruleService;
        this.ingredientRepo = ingredientRepo;
    }

    @PostMapping
    public InteractionRule addRule(@RequestBody InteractionRuleRequest request) {

        ActiveIngredient ingredientA = ingredientRepo.findById(request.getIngredientAId())
                .orElseThrow(() -> new IllegalArgumentException("IngredientA not found"));
        ActiveIngredient ingredientB = ingredientRepo.findById(request.getIngredientBId())
                .orElseThrow(() -> new IllegalArgumentException("IngredientB not found"));

        InteractionRule rule = new InteractionRule(
                ingredientA,
                ingredientB,
                request.getSeverity(),
                request.getDescription()
        );

        return ruleService.addRule(rule);
    }

    @GetMapping
    public List<InteractionRule> getAllRules() {
        return ruleService.getAllRules();
    }
}
