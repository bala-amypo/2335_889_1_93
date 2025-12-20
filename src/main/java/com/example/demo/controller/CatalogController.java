package com.example.demo.controller;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/ingredient")
    public ResponseEntity<List<ActiveIngredient>> getAllIngredients() {
        return ResponseEntity.ok(catalogService.getAllIngredients());
    }

    @GetMapping("/ingredient/{id}")
    public ResponseEntity<ActiveIngredient> getIngredientById(@PathVariable Long id) {
        ActiveIngredient ingredient = catalogService.getIngredientById(id);
        if (ingredient == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ingredient);
    }

    @PostMapping("/ingredient")
    public ResponseEntity<ActiveIngredient> createIngredient(@RequestBody ActiveIngredient ingredient) {
        ActiveIngredient saved = catalogService.createIngredient(ingredient);
        return ResponseEntity.ok(saved);
    }
}
