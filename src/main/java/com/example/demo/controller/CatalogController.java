package com.example.demo.controller;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.Medication;
import com.example.demo.repository.ActiveIngredientRepository;
import com.example.demo.repository.MedicationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        if (ingredientRepo.findByName(ingredient.getName()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Ingredient already exists: " + ingredient.getName());
        }
        return ResponseEntity.ok(ingredientRepo.save(ingredient));
    }

    // Create a new medication with existing ingredients
    @PostMapping("/medication")
    public ResponseEntity<?> createMedication(@RequestBody Medication medication) {
        // Validate that all ingredients exist
        for (ActiveIngredient ing : medication.getIngredients()) {
            ingredientRepo.findById(ing.getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Ingredient not found with ID " + ing.getId()));
        }
        return ResponseEntity.ok(medicationRepo.save(medication));
    }

    // List all medications
    @GetMapping("/medications")
    public List<Medication> listMedications() {
        return medicationRepo.findAll();
    }
}
