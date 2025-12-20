package com.example.demo.controller;

import com.example.demo.service.InteractionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interactions")
public class InteractionController {

    private final InteractionService interactionService;

    public InteractionController(InteractionService interactionService) {
        this.interactionService = interactionService;
    }

    // Example endpoint
    @GetMapping("/{id}")
    public String getInteraction(@PathVariable Long id) {
        // Replace with your real logic
        return "Interaction for ingredient " + id;
    }
}

