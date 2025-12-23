package com.example.demo.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.Medication;
import com.example.demo.repository.ActiveIngredientRepository;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.service.CatalogService;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final ActiveIngredientRepository ingredientRepository;
    private final MedicationRepository medicationRepository;

    public CatalogServiceImpl(
            ActiveIngredientRepository ingredientRepository,
            MedicationRepository medicationRepository) {
        this.ingredientRepository = ingredientRepository;
        this.medicationRepository = medicationRepository;
    }

    @Override
    public ActiveIngredient addIngredient(ActiveIngredient ingredient) {
        ingredient.setId(null); // 🔐 safety
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Medication addMedication(Medication medication) {

        Set<ActiveIngredient> managedIngredients = new HashSet<>();

        for (ActiveIngredient ing : medication.getIngredients()) {

            // ✅ Treat id = 0 as NEW
            if (ing.getId() == null || ing.getId() <= 0) {
                ing.setId(null);
                managedIngredients.add(ingredientRepository.save(ing));
            } else {
                managedIngredients.add(
                        ingredientRepository.findById(ing.getId())
                                .orElseThrow(() ->
                                        new RuntimeException(
                                                "Ingredient not found: " + ing.getId()))
                );
            }
        }

        medication.setIngredients(managedIngredients);
        medication.setId(null); // 🔐 safety
        return medicationRepository.save(medication);
    }

    @Override
    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }
}
