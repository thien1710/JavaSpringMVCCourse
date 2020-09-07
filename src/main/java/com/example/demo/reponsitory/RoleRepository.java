package com.example.demo.reponsitory;

import java.util.Optional;

import com.example.demo.model.role.Role;
import com.example.demo.model.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
