package com.example.demo.repository;

import com.example.demo.entity.InteractionCheckResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionCheckResultRepository extends JpaRepository<InteractionCheckResult, Long> {
    // Standard CRUD methods (save, findById, findAll) are sufficient
}
