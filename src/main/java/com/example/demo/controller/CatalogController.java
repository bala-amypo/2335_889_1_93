package com.example.demo.controller;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.Medication;
import com.example.demo.service.CatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    // POST /catalog/ingredient
    @PostMapping("/ingredient")
    public ActiveIngredient addIngredient(@RequestBody ActiveIngredient ingredient) {
        return catalogService.addIngredient(ingredient);
    }

    // POST /catalog/medication
    @PostMapping("/medication")
    public Medication addMedication(@RequestParam String name,
                                    @RequestBody List<Long> ingredientIds) {
        return catalogService.addMedication(name, ingredientIds);
    }

    // GET /catalog/medications
    @GetMapping("/medications")
    public List<Medication> getAllMedications() {
        return catalogService.getAllMedications();
    }
}
