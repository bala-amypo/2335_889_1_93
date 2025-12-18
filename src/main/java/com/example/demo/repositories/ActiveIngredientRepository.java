package com.example.demo.repositories;

import com.example.demo.models.User;
import com.example.demo.models.ActiveIngredient;
import com.example.demo.models.Medication;
import com.example.demo.models.InteractionRule;
import com.example.demo.models.InteractionCheckResult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}

@Repository
public interface ActiveIngredientRepository extends JpaRepository<ActiveIngredient, Long> {
    Optional<ActiveIngredient> findByName(String name);
    List<ActiveIngredient> findByMedicationId(Long medicationId);
}

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    Optional<Medication> findByName(String name);
    List<Medication> findByActiveIngredientsId(Long activeIngredientId);
}

@Repository
public interface InteractionRuleRepository extends JpaRepository<InteractionRule, Long> {
    List<InteractionRule> findByActiveIngredient1IdOrActiveIngredient2Id(Long id1, Long id2);
}

@Repository
public interface InteractionCheckResultRepository extends JpaRepository<InteractionCheckResult, Long> {
    List<InteractionCheckResult> findByMedication1IdOrMedication2Id(Long med1, Long med2);
}
