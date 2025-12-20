package com.example.demo.controller;

import com.example.demo.entity.InteractionRule;
import com.example.demo.repository.InteractionRuleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rules")
public class InteractionRuleController {

    private final InteractionRuleRepository ruleRepository;

    public InteractionRuleController(InteractionRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @GetMapping
    public List<InteractionRule> getAllRules() {
        return ruleRepository.findAll();
    }

    @GetMapping("/{id}")
    public InteractionRule getRuleById(@PathVariable Long id) {
        return ruleRepository.findById(id).orElse(null);
    }

    @PostMapping
    public InteractionRule createRule(@RequestBody InteractionRule rule) {
        return ruleRepository.save(rule);
    }

    @PutMapping("/{id}")
    public InteractionRule updateRule(@PathVariable Long id, @RequestBody InteractionRule ruleDetails) {
        Optional<InteractionRule> optionalRule = ruleRepository.findById(id);
        if (optionalRule.isPresent()) {
            InteractionRule rule = optionalRule.get();
            rule.setDescription(ruleDetails.getDescription());
            rule.setMedication1(ruleDetails.getMedication1());
            rule.setMedication2(ruleDetails.getMedication2());
            return ruleRepository.save(rule);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteRule(@PathVariable Long id) {
        ruleRepository.deleteById(id);
    }
}
