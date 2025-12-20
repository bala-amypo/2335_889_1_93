package com.example.demo.controller;

import com.example.demo.model.InteractionRule;
import com.example.demo.service.RuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rules")
public class RuleController {

    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    // POST /rules
    @PostMapping
    public InteractionRule addRule(@RequestBody Map<String, Object> request) {

        Long ingredientA = Long.valueOf(request.get("ingredientA").toString());
        Long ingredientB = Long.valueOf(request.get("ingredientB").toString());
        String severity = request.get("severity").toString();
        String description = request.get("description").toString();

        return ruleService.addRule(ingredientA, ingredientB, severity, description);
    }

    // GET /rules
    @GetMapping
    public List<InteractionRule> getAllRules() {
        return ruleService.getAllRules();
    }
}
