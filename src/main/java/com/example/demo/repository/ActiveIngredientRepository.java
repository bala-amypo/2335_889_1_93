package com.example.demo.repository;

import com.example.demo.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;



public interface ActiveIngredientRepository extends JpaRepository<ActiveIngredient, Long> {
    boolean existsByName(String name);
}

