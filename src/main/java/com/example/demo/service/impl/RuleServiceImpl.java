package com.example.demo.service.impl;

import com.example.demo.model.InteractionRule;
import com.example.demo.repository.InteractionRuleRepository;
import com.example.demo.service.RuleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleServiceImpl implements RuleService {

    private final InteractionRuleRepository repository;

    public RuleServiceImpl(InteractionRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public InteractionRule saveRule(InteractionRule rule) {
        return repository.save(rule);
    }

    @Override
    public List<InteractionRule> getAllRules() {
        return repository.findAll();
    }

    @Override
    public InteractionRule getRuleById(Long id) {
        Optional<InteractionRule> rule = repository.findById(id);
        return rule.orElse(null);
    }

    @Override
    public void deleteRule(Long id) {
        repository.deleteById(id);
    }
}
