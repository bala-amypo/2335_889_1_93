// package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.InteractionCheckResult;

@Repository
public interface InteractionCheckResultRepository extends JpaRepository<InteractionCheckResult, Long> {

    // Optional helper: find results by medication name
    // List<InteractionCheckResult> findByMedicationsContaining(String medicationName);
}
