package com.example.demo.controller;

import com.example.demo.model.InteractionRule;
import com.example.demo.service.impl.RuleServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
public class RuleController {

    private final RuleServiceImpl ruleService;

    public RuleController(RuleServiceImpl ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping
    public InteractionRule addRule(@RequestBody InteractionRule rule) {
        return ruleService.addRule(rule);
    }

    @GetMapping
    public List<InteractionRule> getAllRules() {
        return ruleService.getAllRules();
    }
}
