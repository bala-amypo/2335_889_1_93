package com.example.demo.services;

import com.example.demo.models.InteractionCheckResult;

import java.util.List;

public interface InteractionService {
    InteractionCheckResult checkInteractions(List<Long> medicationIds);
    InteractionCheckResult getResult(Long resultId);
}