package com.example.demo.service.impl;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.Medication;
import com.example.demo.repository.ActiveIngredientRepository;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.service.CatalogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {
    
    private final ActiveIngredientRepository activeIngredientRepository;
    private final MedicationRepository medicationRepository;
    
    public CatalogServiceImpl(ActiveIngredientRepository activeIngredientRepository,
                            MedicationRepository medicationRepository) {
        this.activeIngredientRepository = activeIngredientRepository;
        this.medicationRepository = medicationRepository;
    }
    
    // Required methods from STEP 4
    @Override
    public ActiveIngredient addIngredient(ActiveIngredient ingredient) {
        // Check if name already exists
        if (activeIngredientRepository.existsByName(ingredient.getName())) {
            throw new RuntimeException("Ingredient already exists: " + ingredient.getName());
        }
        return activeIngredientRepository.save(ingredient);
    }
    
    @Override
    public Medication addMedication(Medication medication) {
        // Validate: Must have at least 1 ingredient
        if (medication.getIngredients() == null || medication.getIngredients().isEmpty()) {
            throw new RuntimeException("Medication must include at least 1 ingredient");
        }
        return medicationRepository.save(medication);
    }
    
    @Override
    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }
    
    // Additional CRUD methods
    @Override
    public ActiveIngredient updateIngredient(Long id, ActiveIngredient ingredient) {
        ActiveIngredient existing = activeIngredientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + id));
        
        // Check if new name already exists (different from current)
        if (!existing.getName().equals(ingredient.getName()) && 
            activeIngredientRepository.existsByName(ingredient.getName())) {
            throw new RuntimeException("Ingredient name already exists: " + ingredient.getName());
        }
        
        existing.setName(ingredient.getName());
        return activeIngredientRepository.save(existing);
    }
    
    @Override
    public ActiveIngredient getIngredientById(Long id) {
        return activeIngredientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + id));
    }
    
    @Override
    public List<ActiveIngredient> getAllIngredients() {
        return activeIngredientRepository.findAll();
    }
    
    @Override
    public void deleteIngredient(Long id) {
        if (!activeIngredientRepository.existsById(id)) {
            throw new RuntimeException("Ingredient not found with id: " + id);
        }
        activeIngredientRepository.deleteById(id);
    }
    
    @Override
    public Medication updateMedication(Long id, Medication medication) {
        Medication existing = medicationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medication not found with id: " + id));
        
        existing.setName(medication.getName());
        
        // Update ingredients if provided
        if (medication.getIngredients() != null) {
            existing.setIngredients(medication.getIngredients());
        }
        
        return medicationRepository.save(existing);
    }
    
    @Override
    public Medication getMedicationById(Long id) {
        return medicationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medication not found with id: " + id));
    }
    
    @Override
    public void deleteMedication(Long id) {
        if (!medicationRepository.existsById(id)) {
            throw new RuntimeException("Medication not found with id: " + id);
        }
        medicationRepository.deleteById(id);
    }
    
    @Override
    public List<Medication> getMedicationsByIngredientId(Long ingredientId) {
        // First verify ingredient exists
        if (!activeIngredientRepository.existsById(ingredientId)) {
            throw new RuntimeException("Ingredient not found with id: " + ingredientId);
        }
        
        // Get all medications and filter by ingredient
        return medicationRepository.findAll().stream()
            .filter(med -> med.getIngredients().stream()
                .anyMatch(ing -> ing.getId().equals(ingredientId)))
            .toList();
    }
}