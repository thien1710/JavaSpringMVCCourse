package com.example.demo.reponsitory;

import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Boolean existsByUsername(@NotBlank String username);

    Boolean existsByEmail(@NotBlank String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    User findByEmail(String email);

    User findById(long id);

    User findByUsername(String username);

    @Query(value = "SELECT u FROM User u WHERE CONCAT(u.id, u.email, u.firstName, u.lastName) LIKE %:sort%")
    List<User> myCustomQuery(@Param("sort") String sort);

    @Query(value = "SELECT p FROM Project p, Customer c " +
            "WHERE c.id = p.customer.id and c.customerName = ?1 and c.user.id = ?2")
    List<Project> myCustomQueryProject(String sort, long input2);

    User getUserById(Long id);

}