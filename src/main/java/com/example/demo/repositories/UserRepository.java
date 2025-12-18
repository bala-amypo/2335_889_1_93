package com.example.demo.models;

import com.example.demo.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // REQUIRED BY TEST CASES: Basic finders
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    
    // Case-insensitive searches
    Optional<User> findByUsernameIgnoreCase(String username);
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);
    
    // Find by multiple criteria
    Optional<User> findByUsernameAndEmail(String username, String email);
    
    // Search methods
    List<User> findByUsernameContainingIgnoreCase(String username);
    List<User> findByUsernameContainingIgnoreCase(String username, Sort sort);
    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
    
    List<User> findByEmailContainingIgnoreCase(String email);
    
    // Find by username or email
    @Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:identifier) OR LOWER(u.email) = LOWER(:identifier)")
    Optional<User> findByUsernameOrEmail(@Param("identifier") String identifier);
    
    // Find by multiple usernames or emails
    List<User> findByUsernameIn(Collection<String> usernames);
    List<User> findByEmailIn(Collection<String> emails);
    List<User> findByIdIn(Collection<Long> ids);
    
    // Validation methods (useful for updates)
    boolean existsByUsernameAndIdNot(String username, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByUsernameIgnoreCaseAndIdNot(String username, Long id);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
    
    // Find by role (if you have a role field)
    List<User> findByRole(String role);
    List<User> findByRoleIn(Collection<String> roles);
    long countByRole(String role);
    
    // Find by active status (if you have an active/enabled field)
    List<User> findByActive(boolean active);
    List<User> findByActiveTrue();
    List<User> findByActiveFalse();
    long countByActive(boolean active);
    
    // Pagination and sorting
    Page<User> findAll(Pageable pageable);
    List<User> findAllByOrderByUsernameAsc();
    List<User> findAllByOrderByUsernameDesc();
    List<User> findAllByOrderByCreatedAtDesc();
    
    // Date-based queries (if you have createdAt/updatedAt fields)
    List<User> findByCreatedAtAfter(LocalDateTime date);
    List<User> findByCreatedAtBefore(LocalDateTime date);
    List<User> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT u FROM User u WHERE u.createdAt >= :sinceDate ORDER BY u.createdAt DESC")
    List<User> findRecentUsers(@Param("sinceDate") LocalDateTime sinceDate);
    
    // Count methods
    long countByUsernameContaining(String username);
    long countByEmailContaining(String email);
    
    // Delete methods
    @Transactional
    @Modifying
    long deleteByUsername(String username);
    
    @Transactional
    @Modifying
    long deleteByEmail(String email);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.username = :username")
    int deleteByUsernameCustom(@Param("username") String username);
    
    // Update methods
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
    int updatePassword(@Param("id") Long id, @Param("password") String password);
    
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.email = :email WHERE u.id = :id")
    int updateEmail(@Param("id") Long id, @Param("email") String email);
    
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.active = :active WHERE u.id = :id")
    int updateActiveStatus(@Param("id") Long id, @Param("active") boolean active);
    
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.lastLogin = :lastLogin WHERE u.id = :id")
    int updateLastLogin(@Param("id") Long id, @Param("lastLogin") LocalDateTime lastLogin);
    
    // Advanced queries
    
    // Find users by partial username or email match
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchByUsernameOrEmail(@Param("keyword") String keyword);
    
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<User> searchByUsernameOrEmail(@Param("keyword") String keyword, Pageable pageable);
    
    // Find users who haven't logged in recently (if you have lastLogin field)
    @Query("SELECT u FROM User u WHERE u.lastLogin < :cutoffDate OR u.lastLogin IS NULL")
    List<User> findInactiveUsers(@Param("cutoffDate") LocalDateTime cutoffDate);
    
    // Statistics
    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :startDate")
    long countUsersCreatedSince(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT u.role, COUNT(u) FROM User u GROUP BY u.role")
    List<Object[]> countUsersByRole();
    
    // Bulk operations
    @Query("SELECT u FROM User u WHERE u.id BETWEEN :startId AND :endId")
    List<User> findByIdRange(@Param("startId") Long startId, @Param("endId") Long endId);
    
    // Native query example (if needed for complex operations)
    @Query(value = "SELECT * FROM users WHERE LOWER(username) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(email) LIKE LOWER(CONCAT('%', :search, '%'))", nativeQuery = true)
    List<User> searchUsersNative(@Param("search") String search);
}