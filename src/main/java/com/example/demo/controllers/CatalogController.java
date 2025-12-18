package com.example.demo.controllers;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.Medication;
import com.example.demo.service.CatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
@Tag(name = "Catalog", description = "Medication catalog management endpoints")
@SecurityRequirement(name = "bearer-key")
public class CatalogController {
    
    private final CatalogService catalogService;
    
    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }
    
    @PostMapping("/ingredient")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a new active ingredient")
    public ResponseEntity<?> addIngredient(@RequestBody ActiveIngredient ingredient) {
        try {
            ActiveIngredient savedIngredient = catalogService.addIngredient(ingredient);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedIngredient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PostMapping("/medication")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a new medication")
    public ResponseEntity<?> addMedication(@RequestBody Medication medication) {
        try {
            Medication savedMedication = catalogService.addMedication(medication);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMedication);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/medications")
    @Operation(summary = "Get all medications")
    public ResponseEntity<?> getAllMedications() {
        try {
            List<Medication> medications = catalogService.getAllMedications();
            return ResponseEntity.ok(medications);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}