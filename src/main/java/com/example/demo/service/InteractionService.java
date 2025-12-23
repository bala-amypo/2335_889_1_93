package com.example.demo.service;

import java.util.List;

import com.example.demo.model.InteractionCheckResult;

public interface InteractionService {

    InteractionCheckResult checkInteractions(List<Long> medicationIds);

    InteractionCheckResult getResult(Long resultId);
}
