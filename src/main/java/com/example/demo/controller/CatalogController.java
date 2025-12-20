package com.example.demo.controller;

import com.example.demo.entity.Catalog;
import com.example.demo.entity.Medication;
import com.example.demo.repository.CatalogRepository;
import com.example.demo.repository.MedicationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/catalogs")
public class CatalogController {

    private final CatalogRepository catalogRepository;
    private final MedicationRepository medicationRepository;

    public CatalogController(CatalogRepository catalogRepository, MedicationRepository medicationRepository) {
        this.catalogRepository = catalogRepository;
        this.medicationRepository = medicationRepository;
    }

    @GetMapping
    public List<Catalog> getAllCatalogs() {
        return catalogRepository.findAll();
    }

    @GetMapping("/{id}")
    public Catalog getCatalogById(@PathVariable Long id) {
        return catalogRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Catalog createCatalog(@RequestBody Catalog catalog) {
        return catalogRepository.save(catalog);
    }

    @PutMapping("/{id}")
    public Catalog updateCatalog(@PathVariable Long id, @RequestBody Catalog catalogDetails) {
        Optional<Catalog> optionalCatalog = catalogRepository.findById(id);
        if (optionalCatalog.isPresent()) {
            Catalog catalog = optionalCatalog.get();
            catalog.setName(catalogDetails.getName());
            catalog.setMedications(catalogDetails.getMedications());
            return catalogRepository.save(catalog);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteCatalog(@PathVariable Long id) {
        catalogRepository.deleteById(id);
    }
}
