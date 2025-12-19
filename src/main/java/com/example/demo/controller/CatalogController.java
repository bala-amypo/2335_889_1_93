package com.example.demo.controller;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.Medication;
import com.example.demo.service.CatalogService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PostMapping("/ingredient")
    public ActiveIngredient addIngredient(
            @Valid @RequestBody ActiveIngredient ingredient) {
        return catalogService.addIngredient(ingredient);
    }

    @PostMapping("/medication")
    public Medication addMedication(
            @Valid @RequestBody Medication medication) {
        return catalogService.addMedication(medication);
    }

    @GetMapping("/medications")
    public List<Medication> getAllMedications() {
        return catalogService.getAllMedications();
    }
}
