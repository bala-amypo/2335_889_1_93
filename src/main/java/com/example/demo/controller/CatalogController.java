package com.example.demo.controller;

import com.example.demo.dto.MedicationRequest;
import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.Medication;
import com.example.demo.service.CatalogService;
import com.example.demo.repository.ActiveIngredientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogService catalogService;
    private final ActiveIngredientRepository ingredientRepo;

    public CatalogController(CatalogService catalogService, ActiveIngredientRepository ingredientRepo) {
        this.catalogService = catalogService;
        this.ingredientRepo = ingredientRepo;
    }

    // -------------------- Ingredient --------------------
    @PostMapping("/ingredient")
    public ActiveIngredient addIngredient(@RequestBody ActiveIngredient ingredient) {
        return catalogService.addIngredient(ingredient);
    }

    @GetMapping("/ingredients")
    public List<ActiveIngredient> getAllIngredients() {
        return catalogService.getAllIngredients();
    }

    // -------------------- Medication --------------------
    @PostMapping("/medication")
    public Medication addMedication(@RequestBody MedicationRequest request) {

        Medication medication = new Medication(request.getName());
        Set<ActiveIngredient> ingredients = new HashSet<>();

        for (Long id : request.getIngredientIds()) {
            ActiveIngredient ingredient = ingredientRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Ingredient not found: " + id));
            ingredients.add(ingredient);
        }

        medication.setIngredients(ingredients);
        return catalogService.addMedication(medication);
    }

    @GetMapping("/medications")
    public List<Medication> getAllMedications() {
        return catalogService.getAllMedications();
    }
}
