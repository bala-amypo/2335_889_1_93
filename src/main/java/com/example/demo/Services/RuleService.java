package com.example.demo.services;

import com.example.demo.models.InteractionRule;

import java.util.List;

public interface RuleService {
    InteractionRule addRule(InteractionRule rule);
    List<InteractionRule> getAllRules();
}