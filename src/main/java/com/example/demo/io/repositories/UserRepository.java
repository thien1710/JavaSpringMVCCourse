package com.example.demo.io.repositories;

import com.example.demo.io.entity.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserID(String userID);
}
