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

    // POST: Create new InteractionRule
    @PostMapping
    public ResponseEntity<InteractionRule> createRule(@RequestBody InteractionRule rule) {
        InteractionRule savedRule = ruleService.saveRule(rule);
        return ResponseEntity.ok(savedRule);
    }

    // GET: Retrieve all InteractionRules
    @GetMapping
    public ResponseEntity<List<InteractionRule>> getAllRules() {
        List<InteractionRule> rules = ruleService.getAllRules();
        return ResponseEntity.ok(rules);
    }
}
