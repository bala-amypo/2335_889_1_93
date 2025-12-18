package com.example.demo.controllers;

import com.example.demo.model.InteractionCheckResult;
import com.example.demo.service.InteractionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interact")
@Tag(name = "Interaction Checking", description = "Medication interaction checking endpoints")
@SecurityRequirement(name = "bearer-key")
public class InteractionController {
    
    private final InteractionService interactionService;
    
    @Autowired
    public InteractionController(InteractionService interactionService) {
        this.interactionService = interactionService;
    }
    
    @PostMapping("/check")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Check interactions between medications")
    public ResponseEntity<?> checkInteractions(@RequestBody List<Long> medicationIds) {
        try {
            // Validate input
            if (medicationIds == null || medicationIds.size() < 2) {
                return ResponseEntity.badRequest().body("Error: At least 2 medication IDs required");
            }
            
            InteractionCheckResult result = interactionService.checkInteractions(medicationIds);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/result/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get previous interaction check result")
    public ResponseEntity<?> getResult(@PathVariable("id") long resultId) {
        try {
            InteractionCheckResult result = interactionService.getResult(resultId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}