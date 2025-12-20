package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.InteractionCheckResult;
import com.example.demo.model.InteractionRule;
import com.example.demo.model.Medication;
import com.example.demo.repository.InteractionCheckResultRepository;
import com.example.demo.repository.InteractionRuleRepository;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.service.InteractionService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InteractionServiceImpl implements InteractionService {

    private final MedicationRepository medicationRepository;
    private final InteractionRuleRepository ruleRepository;
    private final InteractionCheckResultRepository resultRepository;

    public InteractionServiceImpl(MedicationRepository medicationRepository,
                                  InteractionRuleRepository ruleRepository,
                                  InteractionCheckResultRepository resultRepository) {
        this.medicationRepository = medicationRepository;
        this.ruleRepository = ruleRepository;
        this.resultRepository = resultRepository;
    }

    @Override
    public InteractionCheckResult checkInteractions(List<Long> medicationIds) {
        List<Medication> medications = medicationRepository.findAllById(medicationIds);
        if (medications.isEmpty()) {
            throw new ResourceNotFoundException("No medications found for the provided IDs");
        }

        Set<Long> ingredientIds = new HashSet<>();
        Map<Long, String> ingredientIdToName = new HashMap<>();

        for (Medication med : medications) {
            for (var ing : med.getIngredients()) {
                ingredientIds.add(ing.getId());
                ingredientIdToName.put(ing.getId(), ing.getName());
            }
        }

        List<InteractionRule> allRules = ruleRepository.findAll();

        List<Map<String, String>> interactions = new ArrayList<>();
        for (InteractionRule rule : allRules) {
            if (ingredientIds.contains(rule.getIngredientA().getId()) &&
                ingredientIds.contains(rule.getIngredientB().getId())) {

                Map<String, String> interaction = new HashMap<>();
                interaction.put("ingredientA", rule.getIngredientA().getName());
                interaction.put("ingredientB", rule.getIngredientB().getName());
                interaction.put("severity", rule.getSeverity());
                interaction.put("description", rule.getDescription());
                interactions.add(interaction);
            }
        }

        String medNames = medications.stream().map(Medication::getName).collect(Collectors.joining(", "));
        String interactionsJson = interactions.isEmpty() ? "[]" : interactions.toString();

        InteractionCheckResult result = new InteractionCheckResult(medNames, interactionsJson);
        return resultRepository.save(result);
    }

    @Override
    public InteractionCheckResult getResult(Long resultId) {
        return resultRepository.findById(resultId)
                .orElseThrow(() -> new ResourceNotFoundException("Interaction check result not found with ID: " + resultId));
    }
}
