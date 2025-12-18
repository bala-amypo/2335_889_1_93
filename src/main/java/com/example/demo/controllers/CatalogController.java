package com.example.demo.controllers;

import com.example.demo.models.ActiveIngredient;
import com.example.demo.models.Medication;
import com.example.demo.services.CatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalog")
@Tag(name = "Catalog", description = "Medication Catalog API")
@CrossOrigin(origins = "*")
public class CatalogController {
    
    @Autowired
    private CatalogService catalogService;
    
    @PostMapping("/ingredient")
    @Operation(summary = "Add ingredient", description = "Add a new active ingredient to the catalog")
    public ResponseEntity<?> addIngredient(@Valid @RequestBody ActiveIngredient ingredient) {
        try {
            ActiveIngredient savedIngredient = catalogService.addIngredient(ingredient);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedIngredient);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PostMapping("/medication")
    @Operation(summary = "Add medication", description = "Add a new medication to the catalog")
    public ResponseEntity<?> addMedication(@Valid @RequestBody Medication medication) {
        try {
            Medication savedMedication = catalogService.addMedication(medication);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMedication);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @GetMapping("/medications")
    @Operation(summary = "List all medications", description = "Get a list of all medications in the catalog")
    public ResponseEntity<List<Medication>> getAllMedications() {
        List<Medication> medications = catalogService.getAllMedications();
        return ResponseEntity.ok(medications);
    }
}