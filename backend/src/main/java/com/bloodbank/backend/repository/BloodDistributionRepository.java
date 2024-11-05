package com.bloodbank.backend.repository;

import com.bloodbank.backend.model.BloodDistribution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodDistributionRepository extends JpaRepository<BloodDistribution, Long> {
}
