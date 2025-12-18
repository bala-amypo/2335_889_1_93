package com.example.demo.controllers;

import com.example.demo.models.InteractionCheckResult;
import com.example.demo.services.InteractionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interactions")
@Tag(name = "Interaction Checking", description = "Medication interaction checking endpoints")
public class InteractionController {

    private final InteractionService interactionService;

    public InteractionController(InteractionService interactionService) {
        this.interactionService = interactionService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Check interactions between medications")
    public ResponseEntity<InteractionCheckResult> checkInteractions(
            @RequestBody List<Long> medicationIds) {

        if (medicationIds == null || medicationIds.size() < 2) {
            return ResponseEntity.badRequest().build();
        }

        InteractionCheckResult result = interactionService.checkInteractions(medicationIds);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get previous interaction check result")
    public ResponseEntity<InteractionCheckResult> getResult(@PathVariable Long id) {
        return ResponseEntity.ok(interactionService.getResult(id));
    }
}
