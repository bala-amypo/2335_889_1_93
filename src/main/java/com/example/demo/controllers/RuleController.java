package com.example.demo.controllers;

import com.example.demo.models.InteractionRule;
import com.example.demo.services.RuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
@Tag(name = "Rules", description = "Interaction Rules API")
public class RuleController {

    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping
    @Operation(
        summary = "Add interaction rule",
        description = "Add a new drug interaction rule"
    )
    public ResponseEntity<?> addRule(@RequestBody InteractionRule rule) {
        try {
            InteractionRule savedRule = ruleService.addRule(rule);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRule);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(
        summary = "List all rules",
        description = "Get a list of all interaction rules"
    )
    public ResponseEntity<List<InteractionRule>> getAllRules() {
        return ResponseEntity.ok(ruleService.getAllRules());
    }
}
