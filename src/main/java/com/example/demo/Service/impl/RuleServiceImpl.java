package com.example.demo.service.impl;

import com.example.demo.model.InteractionRule;
import com.example.demo.repository.InteractionRuleRepository;
import com.example.demo.service.RuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class RuleServiceImpl implements RuleService {
    
    private final InteractionRuleRepository interactionRuleRepository;
    
    public RuleServiceImpl(InteractionRuleRepository interactionRuleRepository) {
        this.interactionRuleRepository = interactionRuleRepository;
    }
    
    // Required methods from STEP 4
    @Override
    public InteractionRule addRule(InteractionRule rule) {
        // Validate rule
        if (rule.getIngredient1() == null || rule.getIngredient2() == null) {
            throw new RuntimeException("Both ingredients are required");
        }
        
        if (rule.getIngredient1().getId().equals(rule.getIngredient2().getId())) {
            throw new RuntimeException("Ingredients must be different");
        }
        
        // Check if rule already exists (A-B or B-A)
        Long id1 = rule.getIngredient1().getId();
        Long id2 = rule.getIngredient2().getId();
        
        if (interactionRuleRepository.findRuleBetweenIngredients(id1, id2).isPresent()) {
            throw new RuntimeException("Rule for this ingredient pair already exists");
        }
        
        return interactionRuleRepository.save(rule);
    }
    
    @Override
    public List<InteractionRule> getAllRules() {
        return interactionRuleRepository.findAll();
    }
    
    // Additional CRUD methods
    @Override
    public InteractionRule updateRule(Long id, InteractionRule rule) {
        InteractionRule existing = interactionRuleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rule not found with id: " + id));
        
        // Update ingredients if changed
        if (!existing.getIngredient1().getId().equals(rule.getIngredient1().getId()) ||
            !existing.getIngredient2().getId().equals(rule.getIngredient2().getId())) {
            
            // Check if new rule already exists
            Long id1 = rule.getIngredient1().getId();
            Long id2 = rule.getIngredient2().getId();
            
            if (interactionRuleRepository.findRuleBetweenIngredients(id1, id2).isPresent()) {
                throw new RuntimeException("Rule for this ingredient pair already exists");
            }
            
            existing.setIngredient1(rule.getIngredient1());
            existing.setIngredient2(rule.getIngredient2());
        }
        
        existing.setSeverity(rule.getSeverity());
        existing.setDescription(rule.getDescription());
        
        return interactionRuleRepository.save(existing);
    }
    
    @Override
    public InteractionRule getRuleById(Long id) {
        return interactionRuleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rule not found with id: " + id));
    }
    
    @Override
    public void deleteRule(Long id) {
        if (!interactionRuleRepository.existsById(id)) {
            throw new RuntimeException("Rule not found with id: " + id);
        }
        interactionRuleRepository.deleteById(id);
    }
    
    @Override
    public List<InteractionRule> getRulesByIngredientId(Long ingredientId) {
        return interactionRuleRepository.findByIngredientId(ingredientId);
    }
}