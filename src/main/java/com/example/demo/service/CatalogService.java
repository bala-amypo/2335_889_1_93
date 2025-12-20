package com.example.demo.service;

import com.example.demo.model.ActiveIngredient;

import java.util.List;

public interface CatalogService {
    List<ActiveIngredient> getAllIngredients();
    ActiveIngredient getIngredientById(Long id);
    ActiveIngredient addIngredient(ActiveIngredient ingredient);
}
