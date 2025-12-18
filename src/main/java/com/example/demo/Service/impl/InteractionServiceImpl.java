package com.example.demo.services;

import com.example.demo.models.ActiveIngredient;
import com.example.demo.models.InteractionCheckResult;
import com.example.demo.models.InteractionRule;
import com.example.demo.models.Medication;
import com.example.demo.repositories.InteractionCheckResultRepository;
import com.example.demo.repositories.InteractionRuleRepository;
import com.example.demo.repositories.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class InteractionServiceImpl implements InteractionService {
    
    @Autowired
    private MedicationRepository medicationRepository;
    
    @Autowired
    private InteractionRuleRepository interactionRuleRepository;
    
    @Autowired
    private InteractionCheckResultRepository interactionCheckResultRepository;
    
    @Override
    public InteractionCheckResult checkInteractions(List<Long> medicationIds) {
        // Fetch all medications
        List<Medication> medications = medicationRepository.findAllById(medicationIds);
        
        if (medications.isEmpty()) {
            throw new RuntimeException("No medications found for the provided IDs");
        }
        
        // Collect all unique active ingredients
        Set<ActiveIngredient> allIngredients = new HashSet<>();
        for (Medication medication : medications) {
            allIngredients.addAll(medication.getActiveIngredients());
        }
        
        // Find interactions between ingredients
        List<InteractionRule> foundInteractions = new ArrayList<>();
        List<ActiveIngredient> ingredientList = new ArrayList<>(allIngredients);
        
        for (int i = 0; i < ingredientList.size(); i++) {
            for (int j = i + 1; j < ingredientList.size(); j++) {
                Long id1 = ingredientList.get(i).getId();
                Long id2 = ingredientList.get(j).getId();
                
                interactionRuleRepository.findRuleBetweenIngredients(id1, id2)
                        .ifPresent(foundInteractions::add);
            }
        }
        
        // Create result object
        InteractionCheckResult result = new InteractionCheckResult();
        result.setMedications(medications.stream()
                .map(Medication::getName)
                .collect(Collectors.joining(", ")));
        result.setInteractionCount(foundInteractions.size());
        result.setCheckedAt(LocalDateTime.now());
        
        // Build interactions description
        if (!foundInteractions.isEmpty()) {
            StringBuilder interactions = new StringBuilder();
            for (InteractionRule rule : foundInteractions) {
                interactions.append(rule.getIngredient1().getName())
                        .append(" + ")
                        .append(rule.getIngredient2().getName())
                        .append(": ")
                        .append(rule.getSeverity())
                        .append(" - ")
                        .append(rule.getDescription())
                        .append("\n");
            }
            result.setInteractions(interactions.toString());
        } else {
            result.setInteractions("No interactions found");
        }
        
        // Save and return
        return interactionCheckResultRepository.save(result);
    }
    
    @Override
    public InteractionCheckResult getResult(Long resultId) {
        return interactionCheckResultRepository.findById(resultId)
                .orElseThrow(() -> new RuntimeException("Result not found with id: " + resultId));
    }
}