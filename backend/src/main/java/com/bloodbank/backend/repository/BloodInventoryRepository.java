package com.bloodbank.backend.repository;

import com.bloodbank.backend.model.BloodInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodInventoryRepository extends JpaRepository<BloodInventory, String> {
}
