package com.example.demo.reponsitory;

import com.example.demo.model.department.Department;
import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByDepartmentName(String name);
    Department findById(long id);
}
