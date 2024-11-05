package com.bloodbank.backend.repository;

import com.bloodbank.backend.model.Donor;
import com.bloodbank.backend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonorRepository extends JpaRepository<Donor, Long> {
    Donor findByPersonId(Long id);
}
