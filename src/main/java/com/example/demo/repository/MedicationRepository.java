// package com.example.demo.repository;

// import org.springframework.data.jpa.repository.JpaRepository;
// import com.example.demo.model.Medication;

// public interface MedicationRepository
//         extends JpaRepository<Medication, Long> {
// }
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Medication;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    // Optional helper methods
    boolean existsByName(String name);
}
