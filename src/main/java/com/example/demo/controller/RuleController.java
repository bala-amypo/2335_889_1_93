package com.example.demo.controller;

import com.example.demo.model.InteractionRule;
import com.example.demo.service.RuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
@CrossOrigin(origins = "*") // allow all origins temporarily
public class RuleController {

    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping
    public InteractionRule createRule(@RequestBody InteractionRule rule) {
        return ruleService.save(rule);
    }

    @GetMapping
    public List<InteractionRule> getAllRules() {
        return ruleService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteRule(@PathVariable Long id) {
        ruleService.deleteRule(id);
    }
}
