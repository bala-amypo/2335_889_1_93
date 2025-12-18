package com.example.demo.service;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.Medication;
import java.util.List;

public interface CatalogService {
    // Required by STEP 4
    ActiveIngredient addIngredient(ActiveIngredient ingredient);
    Medication addMedication(Medication medication);
    List<Medication> getAllMedications();
    
    // Additional CRUD operations
    ActiveIngredient updateIngredient(Long id, ActiveIngredient ingredient);
    ActiveIngredient getIngredientById(Long id);
    List<ActiveIngredient> getAllIngredients();
    void deleteIngredient(Long id);
    
    Medication updateMedication(Long id, Medication medication);
    Medication getMedicationById(Long id);
    void deleteMedication(Long id);
    List<Medication> getMedicationsByIngredientId(Long ingredientId);
}