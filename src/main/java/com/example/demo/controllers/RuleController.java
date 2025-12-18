package com.example.demo.controllers;

import com.example.demo.models.InteractionRule;
import com.example.demo.services.RuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rules")
@Tag(name = "Rules", description = "Interaction Rules API")
@CrossOrigin(origins = "*")
public class RuleController {
    
    @Autowired
    private RuleService ruleService;
    
    @PostMapping("/")
    @Operation(summary = "Add interaction rule", description = "Add a new drug interaction rule")
    public ResponseEntity