package com.example.demo.service;

import com.example.demo.model.InteractionRule;
import java.util.List;

public interface RuleService {
    InteractionRule saveRule(InteractionRule rule); // POST
    List<InteractionRule> getAllRules();            // GET
}
