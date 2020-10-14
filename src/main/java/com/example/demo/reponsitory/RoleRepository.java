package com.example.demo.reponsitory;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.role.Role;
import com.example.demo.model.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
    List<Role> findAllById(long id);

    List<Role> findById(long id);

}
