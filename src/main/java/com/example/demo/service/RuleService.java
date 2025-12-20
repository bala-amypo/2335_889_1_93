package com.example.demo.service;

import com.example.demo.model.*;
import java.util.List;



public interface RuleService {
    InteractionRule addRule(Long ingA, Long ingB, String severity, String description);
    List<InteractionRule> getAllRules();
}

