 package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.Medication;
import com.example.demo.service.CatalogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/catalog")
@Tag(name = "Catalog", description = "Manage medications and active ingredients")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    // ---------------- POST /catalog/ingredient ----------------
    @Operation(summary = "Add a new active ingredient")
    @ApiResponse(responseCode = "201", description = "Ingredient added successfully")
    @PostMapping("/ingredient")
    public ResponseEntity<ActiveIngredient> addIngredient(
            @RequestBody ActiveIngredient ingredient) {

        ActiveIngredient saved = catalogService.addIngredient(ingredient);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ---------------- POST /catalog/medication ----------------
    @Operation(summary = "Add a new medication")
    @ApiResponse(responseCode = "201", description = "Medication added successfully")
    @PostMapping("/medication")
    public ResponseEntity<Medication> addMedication(
            @RequestBody Medication medication) {

        Medication saved = catalogService.addMedication(medication);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ---------------- GET /catalog/medications ----------------
    @Operation(summary = "Get all medications")
    @ApiResponse(responseCode = "200", description = "Medications retrieved successfully")
    @GetMapping("/medications")
    public ResponseEntity<List<Medication>> getAllMedications() {
        List<Medication> list = catalogService.getAllMedications();
        return ResponseEntity.ok(list);
    }
}
