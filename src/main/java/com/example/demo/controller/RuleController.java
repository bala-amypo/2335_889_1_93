package com.example.demo.controller;

import com.example.demo.model.InteractionRule;
import com.example.demo.service.RuleService;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<InteractionRule> addRule(@RequestBody InteractionRule rule) {
        try {
            InteractionRule savedRule = ruleService.addRule(rule);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRule);
        } catch (Exception e) {
            e.printStackTrace(); // log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<InteractionRule>> getAllRules() {
        try {
            List<InteractionRule> rules = ruleService.getAllRules();
            if (rules.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(rules);
        } catch (Exception e) {
            e.printStackTrace(); // log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
