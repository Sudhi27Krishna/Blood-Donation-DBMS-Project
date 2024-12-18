package com.bloodbank.backend.repository;

import com.bloodbank.backend.model.Person;
import com.bloodbank.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsByEmail(String email);

    List<Person> findByBloodType(String bloodType);

    Person findByUser(User user);
}
