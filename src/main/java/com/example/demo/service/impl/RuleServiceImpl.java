package com.example.demo.service;

import com.example.demo.model.InteractionRule;
import com.example.demo.repository.InteractionRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    private final InteractionRuleRepository repository;

    public RuleServiceImpl(InteractionRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public InteractionRule save(InteractionRule rule) {
        return repository.save(rule);
    }

    @Override
    public List<InteractionRule> findAll() {
        return repository.findAll();
    }
}
