package com.example.demo.service;

import com.example.demo.model.*;
import java.util.List;


public interface InteractionService {
    InteractionCheckResult checkInteractions(List<Long> medicationIds);
    InteractionCheckResult getResult(Long id);
}

