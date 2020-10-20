package com.example.demo.reponsitory;

import com.example.demo.model.resetpasswordentity.ResetPasswordEntity;
import org.springframework.data.repository.CrudRepository;

public interface ResetPasswordRepository extends CrudRepository<ResetPasswordEntity, Long> {
    ResetPasswordEntity findByToken(String token);
}
