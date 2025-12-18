package com.example.demo.repositories;

import com.example.demo.models.ActiveIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActiveIngredientRepository extends JpaRepository<ActiveIngredient, Long> {
    
    // REQUIRED BY TEST CASES:
    Optional<ActiveIngredient> findByName(String name);
    
    boolean existsByName(String name);
    
    // Additional methods...
    // (same as above, imports are the same for Spring Data JPA)
}
}