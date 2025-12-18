package com.example.demo.service;

import com.example.demo.model.InteractionRule;
import java.util.List;

public interface RuleService {
    // Required by STEP 4
    InteractionRule addRule(InteractionRule rule);
    List<InteractionRule> getAllRules();
    
    // Additional CRUD operations
    InteractionRule updateRule(Long id, InteractionRule rule);
    InteractionRule getRuleById(Long id);
    void deleteRule(Long id);
    List<InteractionRule> getRulesByIngredientId(Long ingredientId);
}