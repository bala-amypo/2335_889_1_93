package com.example.demo.service;

import com.example.demo.model.*;
import java.util.List;



public interface CatalogService {
    ActiveIngredient addIngredient(ActiveIngredient ingredient);
    Medication addMedication(String name, List<Long> ingredientIds);
    List<Medication> getAllMedications();
}



