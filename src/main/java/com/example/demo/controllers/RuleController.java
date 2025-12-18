package com.example.demo.controllers;

import com.example.demo.model.InteractionRule;
import com.example.demo.service.RuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
@Tag(name = "Interaction Rules", description = "Interaction rule management endpoints")
@SecurityRequirement(name = "bearer-key")
public class RuleController {
    
    private final RuleService ruleService;
    
    @Autowired
    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }
    
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a new interaction rule")
    public ResponseEntity<?> addRule(@RequestBody InteractionRule rule) {
        try {
            InteractionRule savedRule = ruleService.addRule(rule);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRule);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/")
    @Operation(summary = "Get all interaction rules")
    public ResponseEntity<?> getAllRules() {
        try {
            List<InteractionRule> rules = ruleService.getAllRules();
            return ResponseEntity.ok(rules);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}