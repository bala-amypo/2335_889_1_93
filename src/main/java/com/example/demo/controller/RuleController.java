package com.example.demo.controller;

import com.example.demo.model.InteractionRule;
import com.example.demo.repository.InteractionRuleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
public class RuleController {

    private final InteractionRuleRepository ruleRepository;

    public RuleController(InteractionRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @GetMapping
    public List<InteractionRule> getAllRules() {
        return ruleRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<InteractionRule> createRule(@RequestBody InteractionRule rule) {
        // Save directly; severity enum will automatically validate
        InteractionRule saved = ruleRepository.save(rule);
        return ResponseEntity.ok(saved);
    }
}
