package com.bloodbank.backend.repository;

import com.bloodbank.backend.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    boolean existsByEmail(String email);
}
