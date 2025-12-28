
package com.example.demo.service.impl;

import com.example.demo.model.InteractionRule;
import com.example.demo.service.RuleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    // REQUIRED no-arg constructor
    public RuleServiceImpl() {}

    @Override
    public InteractionRule addRule(InteractionRule rule) {
        return rule;
    }

    @Override
    public List<InteractionRule> getAllRules() {
        return new ArrayList<>();
    }
}