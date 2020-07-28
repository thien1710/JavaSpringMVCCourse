package com.example.demo;

import com.example.demo.io.entity.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

}
