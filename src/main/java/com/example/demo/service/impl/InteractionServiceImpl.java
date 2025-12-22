package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Medication;
import com.example.demo.model.InteractionCheckResult;
import com.example.demo.model.InteractionRule;
import com.example.demo.model.ActiveIngredient;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.repository.InteractionRuleRepository;
import com.example.demo.repository.InteractionCheckResultRepository;
import com.example.demo.service.InteractionService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InteractionServiceImpl implements InteractionService {

    private final MedicationRepository medicationRepo;
    private final InteractionRuleRepository ruleRepo;
    private final InteractionCheckResultRepository resultRepo;

    public InteractionServiceImpl(MedicationRepository medicationRepo,
                                  InteractionRuleRepository ruleRepo,
                                  InteractionCheckResultRepository resultRepo) {
        this.medicationRepo = medicationRepo;
        this.ruleRepo = ruleRepo;
        this.resultRepo = resultRepo;
    }

    @Override
    public InteractionCheckResult checkInteractions(List<Long> medicationIds) {

        if (medicationIds == null || medicationIds.isEmpty())
            throw new IllegalArgumentException("Medication IDs required");

        List<Medication> meds = medicationRepo.findAllById(medicationIds);
        if (meds.isEmpty())
            throw new ResourceNotFoundException("No medications found for given IDs");

        // Collect all ingredients
        List<ActiveIngredient> ingredients = new ArrayList<>();
        for (Medication m : meds) {
            ingredients.addAll(m.getIngredients());
        }

        // Check interactions
        List<Map<String, String>> detected = new ArrayList<>();
        for (int i = 0; i < ingredients.size(); i++) {
            for (int j = i + 1; j < ingredients.size(); j++) {
                Optional<InteractionRule> ruleOpt = ruleRepo.findRuleBetweenIngredients(
                        ingredients.get(i).getId(),
                        ingredients.get(j).getId()
                );
                if (ruleOpt.isPresent()) {
                    InteractionRule rule = ruleOpt.get();
                    Map<String, String> map = new HashMap<>();
                    map.put("ingredientA", rule.getIngredientA().getName());
                    map.put("ingredientB", rule.getIngredientB().getName());
                    map.put("severity", rule.getSeverity());
                    map.put("description", rule.getDescription());
                    detected.add(map);
                }
            }
        }

        // Build JSON string
        String interactionsJson = detected.toString();
        String medsString = String.join(",", meds.stream().map(Medication::getName).toList());

        InteractionCheckResult result = new InteractionCheckResult(medsString, interactionsJson);
        return resultRepo.save(result);
    }

    @Override
    public InteractionCheckResult getResult(Long resultId) {
        return resultRepo.findById(resultId)
                .orElseThrow(() -> new ResourceNotFoundException("Result not found"));
    }
}
