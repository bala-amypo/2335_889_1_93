package com.example.demo.services;

import com.example.demo.models.InteractionRule;
import com.example.demo.repositories.InteractionRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RuleServiceImpl implements RuleService {
    
    @Autowired
    private InteractionRuleRepository interactionRuleRepository;
    
    @Override
    public InteractionRule addRule(InteractionRule rule) {
        // Check if rule already exists between these ingredients
        if (interactionRuleRepository.existsBetweenIngredients(
                rule.getIngredient1().getId(), 
                rule.getIngredient2().getId())) {
            throw new RuntimeException("Rule already exists between these ingredients");
        }
        return interactionRuleRepository.save(rule);
    }
    
    @Override
    public List<InteractionRule> getAllRules() {
        return interactionRuleRepository.findAll();
    }
}