package com.example.demo.repositories;

import com.example.demo.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    // Basic CRUD operations provided by JpaRepository
}