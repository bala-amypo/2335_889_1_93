package com.example.demo.services;

import com.example.demo.models.ActiveIngredient;
import com.example.demo.models.Medication;
import com.example.demo.repositories.ActiveIngredientRepository;
import com.example.demo.repositories.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {
    
    @Autowired
    private ActiveIngredientRepository activeIngredientRepository;
    
    @Autowired
    private MedicationRepository medicationRepository;
    
    @Override
    public ActiveIngredient addIngredient(ActiveIngredient ingredient) {
        if (activeIngredientRepository.existsByName(ingredient.getName())) {
            throw new RuntimeException("Ingredient already exists: " + ingredient.getName());
        }
        return activeIngredientRepository.save(ingredient);
    }
    
    @Override
    public Medication addMedication(Medication medication) {
        if (medicationRepository.existsByName(medication.getName())) {
            throw new RuntimeException("Medication already exists: " + medication.getName());
        }
        return medicationRepository.save(medication);
    }
    
    @Override
    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }
}