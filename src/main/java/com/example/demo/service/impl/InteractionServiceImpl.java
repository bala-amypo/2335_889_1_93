package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.InteractionCheckResult;
import com.example.demo.model.Medication;
import com.example.demo.repository.InteractionCheckResultRepository;
import com.example.demo.repository.InteractionRuleRepository;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.service.InteractionService;

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

        List<Medication> medications =
                medicationRepository.findAllById(medicationIds);

        if (medications.isEmpty()) {
            throw new IllegalArgumentException("No medications found");
        }

        String medicationNames = medications.stream()
                .map(Medication::getName)
                .collect(Collectors.joining(", "));

        // Simple JSON-like summary for test helper
        String interactionJson =
                "{ \"totalRules\": " + ruleRepository.count() + " }";

        InteractionCheckResult result =
                new InteractionCheckResult(medicationNames, interactionJson);

        return resultRepository.save(result);
    }

    @Override
    public InteractionCheckResult getResult(Long resultId) {

        return resultRepository.findById(resultId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Interaction result not found: " + resultId
                        ));
    }
}
