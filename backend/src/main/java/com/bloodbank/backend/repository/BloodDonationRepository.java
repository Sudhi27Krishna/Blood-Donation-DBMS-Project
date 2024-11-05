package com.bloodbank.backend.repository;

import com.bloodbank.backend.model.BloodDonation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BloodDonationRepository extends JpaRepository<BloodDonation, Long> {
    List<BloodDonation> findAllByDate(LocalDateTime dateTime);

    List<BloodDonation> findAllByBloodType(String bloodType);
}
