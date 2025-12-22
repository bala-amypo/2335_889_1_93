package com.example.demo.service;

import com.example.demo.model.Medication;
import com.example.demo.model.ActiveIngredient;
import java.util.List;

public interface CatalogService {

    // ActiveIngredient
    ActiveIngredient addIngredient(ActiveIngredient ingredient);
    List<ActiveIngredient> getAllIngredients();

    // Medication
    Medication addMedication(Medication medication);
    List<Medication> getAllMedications();
}
