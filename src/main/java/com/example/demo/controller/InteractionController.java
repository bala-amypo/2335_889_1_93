package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.InteractionCheckResult;
import com.example.demo.service.InteractionService;

@RestController
@RequestMapping("/interact")
public class InteractionController {

    private final InteractionService interactionService;

    public InteractionController(InteractionService interactionService) {
        this.interactionService = interactionService;
    }

    /**
     * POST /interact/check
     * Check drug interactions
     */
    @PostMapping("/check")
    public InteractionCheckResult checkInteractions(
            @RequestBody List<Long> medicationIds) {
        return interactionService.checkInteractions(medicationIds);
    }

    /**
     * GET /interact/result/{id}
     * Get previous interaction result
     */
    @GetMapping("/result/{id}")
    public InteractionCheckResult getResult(
            @PathVariable Long id) {
        return interactionService.getResult(id);
    }
}
