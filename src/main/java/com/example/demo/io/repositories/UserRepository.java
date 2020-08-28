package com.example.demo.io.repositories;

import com.example.demo.io.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserID(String userID);

    @Query("SELECT u FROM users u WHERE "
            + "CONCAT(u.id, u.email, u.firstname, u.lastname)"
            + " LIKE %?1%"
    )
    List<UserEntity> findAllOnUsers(String def);
}
