package com.example.demo.service.impl;

import com.example.demo.model.InteractionRule;
import com.example.demo.repository.InteractionRuleRepository;
import com.example.demo.service.RuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    private final InteractionRuleRepository ruleRepository;

    public RuleServiceImpl(InteractionRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public InteractionRule addRule(InteractionRule rule) {
        return ruleRepository.save(rule);
    }

    @Override
    public List<InteractionRule> getAllRules() {
        return ruleRepository.findAll();
    }

    @Override
    public InteractionRule getRuleById(Long id) {
        return ruleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found with id " + id));
    }

    @Override
    public InteractionRule updateRule(Long id, InteractionRule rule) {
        InteractionRule existing = getRuleById(id);
        existing.setIngredientA(rule.getIngredientA());
        existing.setIngredientB(rule.getIngredientB());
        existing.setSeverity(rule.getSeverity());
        existing.setDescription(rule.getDescription());
        return ruleRepository.save(existing);
    }

    @Override
    public void deleteRule(Long id) {
        ruleRepository.deleteById(id);
    }
}
