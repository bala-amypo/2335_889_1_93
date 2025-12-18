package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.example.demo.service.InteractionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class InteractionServiceImpl implements InteractionService {
    
    private final InteractionRuleRepository interactionRuleRepository;
    private final MedicationRepository medicationRepository;
    private final InteractionCheckResultRepository interactionCheckResultRepository;
    private final ActiveIngredientRepository activeIngredientRepository;
    
    // Thread pool for parallel processing
    private final ExecutorService threadPool;
    
    public InteractionServiceImpl(
            InteractionRuleRepository interactionRuleRepository,
            MedicationRepository medicationRepository,
            InteractionCheckResultRepository interactionCheckResultRepository,
            ActiveIngredientRepository activeIngredientRepository) {
        
        this.interactionRuleRepository = interactionRuleRepository;
        this.medicationRepository = medicationRepository;
        this.interactionCheckResultRepository = interactionCheckResultRepository;
        this.activeIngredientRepository = activeIngredientRepository;
        
        // Initialize thread pool
        this.threadPool = Executors.newFixedThreadPool(5);
    }
    
    // Required methods from STEP 4
    @Override
    public InteractionCheckResult checkInteractions(List<Long> medicationIds) {
        // Validate input
        if (medicationIds == null || medicationIds.size() < 2) {
            throw new IllegalArgumentException("At least 2 medication IDs required");
        }
        
        try {
            // Fetch medications
            List<Medication> medications = new ArrayList<>();
            for (Long id : medicationIds) {
                Medication med = medicationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Medication not found: " + id));
                medications.add(med);
            }
            
            // Get all ingredients from all medications
            Set<ActiveIngredient> allIngredients = new HashSet<>();
            for (Medication med : medications) {
                allIngredients.addAll(med.getIngredients());
            }
            
            // Check for interactions between all ingredient pairs
            List<InteractionRule> foundInteractions = new ArrayList<>();
            List<ActiveIngredient> ingredientList = new ArrayList<>(allIngredients);
            
            // Use thread pool for parallel checking
            List<Callable<Void>> tasks = new ArrayList<>();
            for (int i = 0; i < ingredientList.size(); i++) {
                for (int j = i + 1; j < ingredientList.size(); j++) {
                    final ActiveIngredient ing1 = ingredientList.get(i);
                    final ActiveIngredient ing2 = ingredientList.get(j);
                    
                    tasks.add(() -> {
                        Optional<InteractionRule> rule = interactionRuleRepository
                            .findRuleBetweenIngredients(ing1.getId(), ing2.getId());
                        
                        if (rule.isPresent()) {
                            synchronized (foundInteractions) {
                                foundInteractions.add(rule.get());
                            }
                        }
                        return null;
                    });
                }
            }
            
            // Execute tasks in parallel
            threadPool.invokeAll(tasks);
            
            // Build JSON summary
            String interactionsJson = buildInteractionsSummary(medications, foundInteractions);
            
            // Create result
            InteractionCheckResult result = new InteractionCheckResult();
            result.setMedications(medications.stream()
                .map(Medication::getName)
                .collect(Collectors.joining(",")));
            result.setInteractions(interactionsJson);
            
            // Save result
            return interactionCheckResultRepository.save(result);
            
        } catch (Exception e) {
            throw new RuntimeException("Error checking interactions: " + e.getMessage(), e);
        }
    }
    
    @Override
    public InteractionCheckResult getResult(long resultId) {
        return interactionCheckResultRepository.findById(resultId)
            .orElseThrow(() -> new IllegalArgumentException("Result not found: " + resultId));
    }
    
    // Additional CRUD methods
    @Override
    public List<InteractionCheckResult> getAllResults() {
        return interactionCheckResultRepository.findAll();
    }
    
    @Override
    public void deleteResult(Long id) {
        if (!interactionCheckResultRepository.existsById(id)) {
            throw new RuntimeException("Result not found with id: " + id);
        }
        interactionCheckResultRepository.deleteById(id);
    }
    
    @Override
    public List<InteractionCheckResult> getResultsByMedicationName(String medicationName) {
        return interactionCheckResultRepository.findAll().stream()
            .filter(result -> result.getMedications().contains(medicationName))
            .collect(Collectors.toList());
    }
    
    // Helper method
    private String buildInteractionsSummary(List<Medication> medications, 
                                           List<InteractionRule> interactions) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode root = mapper.createObjectNode();
            
            // Add medications
            ArrayNode medsArray = root.putArray("medications");
            for (Medication med : medications) {
                ObjectNode medNode = medsArray.addObject();
                medNode.put("id", med.getId());
                medNode.put("name", med.getName());
                
                ArrayNode ingArray = medNode.putArray("ingredients");
                for (ActiveIngredient ing : med.getIngredients()) {
                    ingArray.add(ing.getName());
                }
            }
            
            // Add interactions
            ArrayNode interactionsArray = root.putArray("interactions");
            for (InteractionRule rule : interactions) {
                ObjectNode ruleNode = interactionsArray.addObject();
                ruleNode.put("ingredient1", rule.getIngredient1().getName());
                ruleNode.put("ingredient2", rule.getIngredient2().getName());
                ruleNode.put("severity", rule.getSeverity());
                ruleNode.put("description", rule.getDescription());
            }
            
            // Add metadata
            root.put("totalInteractions", interactions.size());
            root.put("checkTimestamp", LocalDateTime.now().toString());
            
            return mapper.writeValueAsString(root);
            
        } catch (Exception e) {
            return "{\"error\": \"Failed to generate summary\"}";
        }
    }
    
    // Cleanup method
    public void shutdown() {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}