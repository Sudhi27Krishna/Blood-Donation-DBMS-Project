package com.bloodbank.backend.repository;

import com.bloodbank.backend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsByEmail(String email);
}
