package com.example.demo.controller;

import com.example.demo.model.InteractionRule;
import com.example.demo.repository.ActiveIngredientRepository;
import com.example.demo.repository.InteractionRuleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rules")
public class RuleController {

    private final InteractionRuleRepository ruleRepo;
    private final ActiveIngredientRepository ingredientRepo;

    public RuleController(InteractionRuleRepository ruleRepo, ActiveIngredientRepository ingredientRepo) {
        this.ruleRepo = ruleRepo;
        this.ingredientRepo = ingredientRepo;
    }

    @PostMapping
    public ResponseEntity<?> createRule(@RequestBody InteractionRule rule) {
        // Ensure ingredients exist
        ingredientRepo.findById(rule.getIngredientA().getId())
                .orElseThrow(() -> new RuntimeException("Ingredient A not found"));
        ingredientRepo.findById(rule.getIngredientB().getId())
                .orElseThrow(() -> new RuntimeException("Ingredient B not found"));

        // Ensure severity is valid
        if (rule.getSeverity() == null) {
            return ResponseEntity.badRequest().body("Severity must be MINOR, MODERATE, or MAJOR");
        }

        return ResponseEntity.ok(ruleRepo.save(rule));
    }
}
