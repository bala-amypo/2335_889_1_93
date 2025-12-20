package com.example.demo.controller;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.Medication;
import com.example.demo.repository.ActiveIngredientRepository;
import com.example.demo.repository.MedicationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final ActiveIngredientRepository ingredientRepo;
    private final MedicationRepository medicationRepo;

    public CatalogController(ActiveIngredientRepository ingredientRepo, MedicationRepository medicationRepo) {
        this.ingredientRepo = ingredientRepo;
        this.medicationRepo = medicationRepo;
    }

    // Create a new active ingredient
    @PostMapping("/ingredient")
    public ResponseEntity<?> createIngredient(@RequestBody ActiveIngredient ingredient) {
        Optional<ActiveIngredient> existingIngredient = ingredientRepo.findByName(ingredient.getName());
        if (existingIngredient.isPresent()) {
            return ResponseEntity.badRequest().body("Ingredient already exists: " + ingredient.getName());
        }
        ActiveIngredient savedIngredient = ingredientRepo.save(ingredient);
        return ResponseEntity.ok(savedIngredient);
    }

    // Create a new medication
    @PostMapping("/medication")
    public ResponseEntity<?> createMedication(@RequestBody Medication medication) {
        // Validate that all ingredients exist
        for (ActiveIngredient ing : medication.getIngredients()) {
            ingredientRepo.findById(ing.getId())
                    .orElseThrow(() -> new RuntimeException("Ingredient not found with ID " + ing.getId()));
        }
        Medication savedMedication = medicationRepo.save(medication);
        return ResponseEntity.ok(savedMedication);
    }

    // List all medications
    @GetMapping("/medications")
    public ResponseEntity<List<Medication>> listMedications() {
        List<Medication> medications = medicationRepo.findAll();
        return ResponseEntity.ok(medications);
    }
}
