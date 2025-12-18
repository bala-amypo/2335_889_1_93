package com.example.demo.services;

import com.example.demo.models.ActiveIngredient;
import com.example.demo.models.Medication;

import java.util.List;

public interface CatalogService {
    ActiveIngredient addIngredient(ActiveIngredient ingredient);
    Medication addMedication(Medication medication);
    List<Medication> getAllMedications();
}