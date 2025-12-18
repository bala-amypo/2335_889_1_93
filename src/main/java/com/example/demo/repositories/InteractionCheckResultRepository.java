package com.example.demo.repositories;

import com.example.demo.models.InteractionCheckResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionCheckResultRepository extends JpaRepository<InteractionCheckResult, Long> {
    // Basic CRUD operations provided by JpaRepository
}