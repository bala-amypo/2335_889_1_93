
package com.example.demo.service.impl;

import com.example.demo.model.InteractionCheckResult;
import com.example.demo.service.InteractionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InteractionServiceImpl implements InteractionService {

   
    public InteractionServiceImpl() {}

    @Override
    public InteractionCheckResult checkInteractions(List<Long> medicationIds) {
        
        InteractionCheckResult result = new InteractionCheckResult();
        return result;
    }

    @Override
    public InteractionCheckResult getResult(Long id) {
        InteractionCheckResult result = new InteractionCheckResult();
        result.setId(id);   
        return result;
    }
}