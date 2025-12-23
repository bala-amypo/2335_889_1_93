package com.example.demo.controller;

import com.example.demo.model.InteractionRule;
import com.example.demo.service.RuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
public class RuleController {

    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    // Create
    @PostMapping
    public ResponseEntity<InteractionRule> createRule(@RequestBody InteractionRule rule) {
        InteractionRule savedRule = ruleService.saveRule(rule);
        return ResponseEntity.ok(savedRule);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<InteractionRule>> getAllRules() {
        return ResponseEntity.ok(ruleService.getAllRules());
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<InteractionRule> getRuleById(@PathVariable Long id) {
        InteractionRule rule = ruleService.getRuleById(id);
        if (rule == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rule);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        ruleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
}
