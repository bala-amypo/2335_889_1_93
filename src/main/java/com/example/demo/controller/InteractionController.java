package com.example.demo.controller;

import com.example.demo.model.InteractionRule;
import com.example.demo.service.RuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
public class InteractionController {

    private final RuleService ruleService;

    public InteractionController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    // CREATE a new rule
    @PostMapping
    public ResponseEntity<InteractionRule> createRule(@RequestBody InteractionRule rule) {
        InteractionRule savedRule = ruleService.saveRule(rule);
        return ResponseEntity.ok(savedRule);
    }

    // GET all rules
    @GetMapping
    public ResponseEntity<List<InteractionRule>> getAllRules() {
        List<InteractionRule> rules = ruleService.getAllRules();
        return ResponseEntity.ok(rules);
    }
}
