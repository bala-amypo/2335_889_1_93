
package com.example.demo.service.impl;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.Medication;
import com.example.demo.service.CatalogService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    
    public CatalogServiceImpl() {}

    @Override
    public ActiveIngredient addIngredient(ActiveIngredient ingredient) {
        return ingredient;
    }

    @Override
    public Medication addMedication(Medication medication) {
        return medication;
    }

    @Override
    public List<Medication> getAllMedications() {
        return new ArrayList<>();
    }
}