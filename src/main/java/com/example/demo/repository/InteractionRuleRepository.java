package com.example.demo.repository;

import com.example.demo.model.InteractionRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionRuleRepository
        extends JpaRepository<InteractionRule, Long> {
}
