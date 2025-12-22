package com.example.demo.controller;

import com.example.demo.model.InteractionCheckResult;
import com.example.demo.service.impl.InteractionServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interact")
public class InteractionController {

    private final InteractionServiceImpl interactionService;

    public InteractionController(InteractionServiceImpl interactionService) {
        this.interactionService = interactionService;
    }

    @PostMapping("/check")
    public InteractionCheckResult checkInteractions(@RequestBody List<Long> medicationIds) {
        return interactionService.checkInteractions(medicationIds);
    }

    @GetMapping("/result/{id}")
    public InteractionCheckResult getResult(@PathVariable Long id) {
        return interactionService.getResult(id);
    }
}
