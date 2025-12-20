package com.example.demo.service.impl;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.InteractionCheckResult;
import com.example.demo.model.InteractionRule;
import com.example.demo.model.Medication;
import com.example.demo.repository.InteractionCheckResultRepository;
import com.example.demo.repository.InteractionRuleRepository;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.service.InteractionService;
import com.example.demo.exception.ResourceNotFoundException;
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
        if (medications.isEmpty()) throw new IllegalArgumentException("No medications found");

        Set<ActiveIngredient> allIngredients = medications.stream()
                .flatMap(m -> m.getIngredients().stream())
                .collect(Collectors.toSet());

        List<InteractionRule> rules = ruleRepository.findAll();

        List<String> detectedInteractions = new ArrayList<>();

        for (InteractionRule rule : rules) {
            if (allIngredients.contains(rule.getIngredientA()) &&
                allIngredients.contains(rule.getIngredientB())) {
                detectedInteractions.add(rule.getIngredientA().getName() + " - " +
                        rule.getIngredientB().getName() + ": " + rule.getSeverity() +
                        " (" + rule.getDescription() + ")");
            }
        }

        String medicationNames = medications.stream().map(Medication::getName).collect(Collectors.joining(", "));
        String interactionsJson = "[" + detectedInteractions.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(",")) + "]";

        InteractionCheckResult result = new InteractionCheckResult(medicationNames, interactionsJson);
        return resultRepository.save(result);
    }

    @Override
    public InteractionCheckResult getResult(Long resultId) {
        return resultRepository.findById(resultId)
                .orElseThrow(() -> new ResourceNotFoundException("Interaction check result not found for ID: " + resultId));
    }
}
