package com.example.demo.repositories;

import com.example.demo.models.InteractionCheckResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InteractionCheckResultRepository extends JpaRepository<InteractionCheckResult, Long> {
    
    // Find results by date range
    List<InteractionCheckResult> findByCheckedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find recent results (last N days)
    @Query("SELECT icr FROM InteractionCheckResult icr WHERE icr.checkedAt >= :sinceDate ORDER BY icr.checkedAt DESC")
    List<InteractionCheckResult> findRecentResults(@Param("sinceDate") LocalDateTime sinceDate);
    
    // Find results containing specific medication
    @Query("SELECT icr FROM InteractionCheckResult icr WHERE LOWER(icr.medications) LIKE LOWER(CONCAT('%', :medicationName, '%'))")
    List<InteractionCheckResult> findByMedicationNameContaining(@Param("medicationName") String medicationName);
    
    // Pagination support
    Page<InteractionCheckResult> findAll(Pageable pageable);
    Page<InteractionCheckResult> findAllByOrderByCheckedAtDesc(Pageable pageable);
    
    // Find by user (if you have user tracking)
    List<InteractionCheckResult> findByUserIdOrderByCheckedAtDesc(Long userId);
    Page<InteractionCheckResult> findByUserId(Long userId, Pageable pageable);
    
    // Count methods
    long countByCheckedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    long countByUserId(Long userId);
    
    // Delete old results (cleanup)
    @Transactional
    @Modifying
    @Query("DELETE FROM InteractionCheckResult icr WHERE icr.checkedAt < :cutoffDate")
    int deleteByCheckedAtBefore(@Param("cutoffDate") LocalDateTime cutoffDate);
    
    // Find results with highest interaction counts - FIXED: Removed LIMIT from JPQL
    @Query("SELECT icr FROM InteractionCheckResult icr WHERE icr.interactionCount > 0 ORDER BY icr.interactionCount DESC, icr.checkedAt DESC")
    List<InteractionCheckResult> findResultsWithInteractions(Pageable pageable);
    
    // Statistics query
    @Query("SELECT COUNT(icr), MAX(icr.checkedAt), AVG(icr.interactionCount) FROM InteractionCheckResult icr WHERE icr.checkedAt >= :startDate")
    Object[] getStatisticsSince(@Param("startDate") LocalDateTime startDate);
    
    // Find by severity level (if you have severity field)
    List<InteractionCheckResult> findBySeverityLevelOrderByCheckedAtDesc(String severityLevel);
    
    // Find results without interactions
    @Query("SELECT icr FROM InteractionCheckResult icr WHERE icr.interactionCount = 0 OR icr.interactionCount IS NULL")
    List<InteractionCheckResult> findResultsWithoutInteractions();
    
    // Bulk find by IDs
    List<InteractionCheckResult> findByIdIn(List<Long> ids);
    
    // Find latest result for a specific user - FIXED: Changed to Optional and use Spring Data method
    Optional<InteractionCheckResult> findFirstByUserIdOrderByCheckedAtDesc(Long userId);
    
    // Alternative: If you need native query with LIMIT
    @Query(value = "SELECT * FROM interaction_check_results WHERE user_id = :userId ORDER BY checked_at DESC LIMIT 1", nativeQuery = true)
    InteractionCheckResult findLatestByUserIdNative(@Param("userId") Long userId);
}