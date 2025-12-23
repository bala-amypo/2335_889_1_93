package com.example.demo.service;

import com.example.demo.model.InteractionRule;
import java.util.List;

public interface RuleService {
    InteractionRule saveRule(InteractionRule rule);
    List<InteractionRule> getAllRules();
    InteractionRule getRuleById(Long id);
    void deleteRule(Long id);
}
