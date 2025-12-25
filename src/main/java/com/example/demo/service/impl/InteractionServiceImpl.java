 package com.example.demo.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.InteractionCheckResult;
import com.example.demo.model.InteractionRule;
import com.example.demo.model.Medication;
import com.example.demo.repository.InteractionCheckResultRepository;
import com.example.demo.repository.InteractionRuleRepository;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.service.InteractionService;

@Service
@Transactional
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
            throw new IllegalArgumentException("No medications found for the given IDs");
        }

        // Collect all active ingredients
        Set<ActiveIngredient> ingredients = medications.stream()
                .flatMap(m -> m.getIngredients().stream())
                .collect(Collectors.toSet());

        // Check interactions between every pair of ingredients
        StringBuilder interactionsBuilder = new StringBuilder();
        List<InteractionRule> rules = ruleRepository.findAll();

        for (InteractionRule rule : rules) {
            if (ingredients.contains(rule.getIngredientA()) && ingredients.contains(rule.getIngredientB())) {
                interactionsBuilder.append(rule.getIngredientA().getName())
                        .append(" + ")
                        .append(rule.getIngredientB().getName())
                        .append(" : ")
                        .append(rule.getSeverity())
                        .append(" - ")
                        .append(rule.getDescription())
                        .append("\n");
            }
        }

        String medicationNames = medications.stream()
                .map(Medication::getName)
                .collect(Collectors.joining(", "));

        String interactions = interactionsBuilder.length() > 0 ? interactionsBuilder.toString() : "No interactions found";

        InteractionCheckResult result = new InteractionCheckResult(medicationNames, interactions);
        return resultRepository.save(result);
    }

    @Override
    public InteractionCheckResult getResult(Long resultId) {
        return resultRepository.findById(resultId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Interaction result not found: " + resultId));
    }
}
