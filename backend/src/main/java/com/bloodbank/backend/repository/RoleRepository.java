package com.bloodbank.backend.repository;

import com.bloodbank.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleUser);
}
