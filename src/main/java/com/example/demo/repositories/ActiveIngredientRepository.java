package com.example.demo.repositories;

import com.example.demo.models.ActiveIngredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ActiveIngredientRepository extends JpaRepository<ActiveIngredient, Long> {
    
    // REQUIRED BY TEST CASES:
    Optional<ActiveIngredient> findByName(String name);
    
    boolean existsByName(String name);
    
    // Custom finder methods with different naming conventions:
    Optional<ActiveIngredient> findByNameIgnoreCase(String name);
    
    List<ActiveIngredient> findByNameContainingIgnoreCase(String name);
    
    List<ActiveIngredient> findByNameContainingIgnoreCase(String name, Sort sort);
    
    Page<ActiveIngredient> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    // Find by multiple names
    List<ActiveIngredient> findByNameIn(List<String> names);
    
    List<ActiveIngredient> findByNameIn(Set<String> names);
    
    // Count methods
    long countByNameContaining(String name);
    
    long countByNameIgnoreCase(String name);
    
    // Delete methods
    @Transactional
    @Modifying
    void deleteByName(String name);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM ActiveIngredient ai WHERE ai.name = :name")
    int deleteByNameCustom(@Param("name") String name);
    
    // Custom JPQL queries
    @Query("SELECT ai FROM ActiveIngredient ai WHERE ai.name LIKE %:keyword%")
    List<ActiveIngredient> searchByName(@Param("keyword") String keyword);
    
    @Query("SELECT ai FROM ActiveIngredient ai WHERE LOWER(ai.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ActiveIngredient> searchByNameCaseInsensitive(@Param("keyword") String keyword);
    
    @Query("SELECT ai FROM ActiveIngredient ai WHERE ai.name LIKE :prefix%")
    List<ActiveIngredient> findByNameStartsWith(@Param("prefix") String prefix);
    
    // Native SQL query (if needed)
    @Query(value = "SELECT * FROM active_ingredients WHERE name ILIKE %:name%", nativeQuery = true)
    List<ActiveIngredient> searchByNameNative(@Param("name") String name);
    
    // Find all with sorting
    List<ActiveIngredient> findAllByOrderByNameAsc();
    
    List<ActiveIngredient> findAllByOrderByNameDesc();
    
    // Find with pagination
    Page<ActiveIngredient> findAll(Pageable pageable);
    
    Page<ActiveIngredient> findAllByOrderByName(Pageable pageable);
    
    // Find by ID list
    List<ActiveIngredient> findByIdIn(List<Long> ids);
    
    // Check existence with exclusion
    boolean existsByNameAndIdNot(String name, Long id);
    
    // Bulk operations
    @Query("SELECT ai FROM ActiveIngredient ai WHERE ai.id BETWEEN :startId AND :endId")
    List<ActiveIngredient> findByIdRange(@Param("startId") Long startId, @Param("endId") Long endId);
}