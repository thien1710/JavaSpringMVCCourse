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

//    c1
//    List<UserEntity> findAll(String fn);

//    c2
//    @Query("SELECT u FROM users u WHERE u.firstname = :def")
//    List<UserEntity> findAll(@Param("def") String def);

//    c3
    @Query("SELECT u FROM users u WHERE u.firstname = ?1")
    List<UserEntity> findAll(String def);
}
