package com.bloodbank.backend.repository;

import com.bloodbank.backend.model.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {
    List<BloodRequest> findByBloodType(String bloodType);

    List<BloodRequest> findByDate(LocalDateTime dateTime);
}
