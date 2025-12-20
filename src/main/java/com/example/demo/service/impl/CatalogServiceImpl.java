package com.example.demo.service.impl;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.repository.ActiveIngredientRepository;
import com.example.demo.service.CatalogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final ActiveIngredientRepository repository;

    public CatalogServiceImpl(ActiveIngredientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ActiveIngredient> getAllIngredients() {
        return repository.findAll();
    }

    @Override
    public ActiveIngredient getIngredientById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + id));
    }

    @Override
    public ActiveIngredient addIngredient(ActiveIngredient ingredient) {
        return repository.save(ingredient);
    }
}
