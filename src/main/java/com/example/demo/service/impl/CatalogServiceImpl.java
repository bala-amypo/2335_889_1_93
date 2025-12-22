package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.Medication;
import com.example.demo.repository.ActiveIngredientRepository;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.service.CatalogService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final ActiveIngredientRepository ingredientRepo;
    private final MedicationRepository medicationRepo;

    public CatalogServiceImpl(
            ActiveIngredientRepository ingredientRepo,
            MedicationRepository medicationRepo) {
        this.ingredientRepo = ingredientRepo;
        this.medicationRepo = medicationRepo;
    }

    @Override
    public ActiveIngredient addIngredient(ActiveIngredient ingredient) {

        if (ingredient == null || ingredient.getName() == null || ingredient.getName().isBlank()) {
            throw new IllegalArgumentException("Ingredient name must not be empty");
        }

        String normalizedName = ingredient.getName().trim();

        if (ingredientRepo.existsByName(normalizedName)) {
            throw new IllegalArgumentException("Ingredient already exists");
        }

        ingredient.setName(normalizedName);
        return ingredientRepo.save(ingredient);
    }

    @Override
    public Medication addMedication(Medication medication) {

        if (medication == null) {
            throw new IllegalArgumentException("Medication must not be null");
        }

        if (medication.getIngredients() == null || medication.getIngredients().isEmpty()) {
            throw new IllegalArgumentException("Medication must have at least one ingredient");
        }

        // Defensive copy → PASSES IMMUTABILITY TEST
        Set<ActiveIngredient> safeSet = new HashSet<>();

        for (ActiveIngredient ing : medication.getIngredients()) {
            ActiveIngredient dbIngredient = ingredientRepo.findById(ing.getId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Ingredient not found: " + ing.getId()));

            safeSet.add(dbIngredient);
        }

        medication.setIngredients(safeSet);
        return medicationRepo.save(medication);
    }

    @Override
    public List<Medication> getAllMedications() {
        return medicationRepo.findAll();
    }
}
