package com.example.demo.service.impl;
import com.example.demo.service.impl.ResourceNotFoundException;



import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.InteractionService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

        List<Medication> meds = medicationRepo.findAllById(medicationIds);

        if (meds.isEmpty()) {
            throw new IllegalArgumentException("No valid medications found");
        }

        Set<ActiveIngredient> ingredients = meds.stream()
                .flatMap(m -> m.getIngredients().stream())
                .collect(Collectors.toSet());

        List<String> detected = new ArrayList<>();

        List<ActiveIngredient> list = new ArrayList<>(ingredients);
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                ruleRepo.findRuleBetweenIngredients(
                        list.get(i).getId(),
                        list.get(j).getId()
                ).ifPresent(rule ->
                        detected.add(rule.getIngredientA().getName()
                                + " + " + rule.getIngredientB().getName()
                                + " (" + rule.getSeverity() + ")")
                );
            }
        }

        String medsNames = meds.stream()
                .map(Medication::getName)
                .collect(Collectors.joining(","));

        String resultJson = detected.toString();

        InteractionCheckResult result =
                new InteractionCheckResult(medsNames, resultJson);

        return resultRepo.save(result);
    }

    @Override
    public InteractionCheckResult getResult(Long id) {
        return resultRepo.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Interaction result not found with id " + id)
            );
    }
}
