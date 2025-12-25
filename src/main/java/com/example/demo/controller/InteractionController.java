// package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.InteractionCheckResult;
import com.example.demo.service.InteractionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/interact")
@Tag(name = "Interaction Check", description = "Check drug interactions and retrieve results")
public class InteractionController {

    private final InteractionService interactionService;

    public InteractionController(InteractionService interactionService) {
        this.interactionService = interactionService;
    }

    // ---------------- POST /interact/check ----------------
    @Operation(summary = "Check interactions for a list of medications")
    @ApiResponse(responseCode = "201", description = "Interaction check completed successfully")
    @PostMapping("/check")
    public ResponseEntity<InteractionCheckResult> checkInteractions(
            @RequestBody List<Long> medicationIds) {

        if (medicationIds == null || medicationIds.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        InteractionCheckResult result = interactionService.checkInteractions(medicationIds);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // ---------------- GET /interact/result/{id} ----------------
    @Operation(summary = "Get a previously saved interaction check result")
    @ApiResponse(responseCode = "200", description = "Interaction check result retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Result not found")
    @GetMapping("/result/{id}")
    public ResponseEntity<InteractionCheckResult> getResult(
            @PathVariable Long id) {

        InteractionCheckResult result = interactionService.getResult(id);
        return ResponseEntity.ok(result);
    }
}
