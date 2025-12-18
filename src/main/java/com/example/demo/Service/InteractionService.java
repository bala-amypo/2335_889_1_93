package com.example.demo.service;

import com.example.demo.model.InteractionCheckResult;
import java.util.List;

public interface InteractionService {
    // Required by STEP 4
    InteractionCheckResult checkInteractions(List<Long> medicationIds);
    InteractionCheckResult getResult(long resultId);
    
    // Additional CRUD operations
    List<InteractionCheckResult> getAllResults();
    void deleteResult(Long id);
    List<InteractionCheckResult> getResultsByMedicationName(String medicationName);
}