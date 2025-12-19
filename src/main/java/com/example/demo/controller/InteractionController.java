package com.example.demo.controller;

import com.example.demo.model.InteractionCheckResult;
import com.example.demo.service.InteractionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interact")
public class InteractionController {

    private final InteractionService interactionService;

    public InteractionController(InteractionService interactionService) {
        this.interactionService = interactionService;
    }

    @PostMapping("/check")
    public InteractionCheckResult check(
            @RequestBody List<Long> medicationIds) {
        return interactionService.checkInteractions(medicationIds);
    }

    @GetMapping("/result/{id}")
    public InteractionCheckResult getResult(@PathVariable Long id) {
        return interactionService.getResult(id);
    }
}
