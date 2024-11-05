package com.bloodbank.backend.repository;

import com.bloodbank.backend.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientRepository extends JpaRepository<Recipient, Long> {
    Recipient findByPersonId(Long id);
}
