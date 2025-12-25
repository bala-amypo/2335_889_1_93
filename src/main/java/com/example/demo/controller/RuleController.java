package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.InteractionRule;
import com.example.demo.service.RuleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/rules")
@Tag(name = "Interaction Rules", description = "Manage interaction rules between active ingredients")
public class RuleController {

    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    // ---------------- POST /rules ----------------
    @Operation(summary = "Add a new interaction rule")
    @ApiResponse(responseCode = "201", description = "Rule created successfully")
    @PostMapping
    public ResponseEntity<InteractionRule> addRule(@RequestBody InteractionRule rule) {
        // Validation
        if (rule.getIngredientA() == null || rule.getIngredientB() == null || rule.getSeverity() == null) {
            return ResponseEntity.badRequest().build();
        }

        InteractionRule savedRule = ruleService.addRule(rule);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRule);
    }

    // ---------------- GET /rules ----------------
    @Operation(summary = "Get all interaction rules")
    @ApiResponse(responseCode = "200", description = "List of all interaction rules")
    @GetMapping
    public ResponseEntity<List<InteractionRule>> getAllRules() {
        List<InteractionRule> rules = ruleService.getAllRules();
        return ResponseEntity.ok(rules);
    }
}
