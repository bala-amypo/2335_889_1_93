package com.example.demo.controller;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.service.CatalogService;
import org.springframework.http.HttpStatus;
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
        List<ActiveIngredient> ingredients = catalogService.getAllIngredients();
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping("/ingredient/{id}")
    public ResponseEntity<ActiveIngredient> getIngredientById(@PathVariable Long id) {
        ActiveIngredient ingredient = catalogService.getIngredientById(id);
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }

    @PostMapping("/ingredient")
    public ResponseEntity<ActiveIngredient> addIngredient(@RequestBody ActiveIngredient ingredient) {
        ActiveIngredient saved = catalogService.addIngredient(ingredient);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
