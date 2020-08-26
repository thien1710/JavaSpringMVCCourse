package com.example.demo.service;

import com.example.demo.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);

    UserDto getUser(String email);

    UserDto getUserByUserID(String userID);

    UserDto updateUser(String userID, UserDto user);

    void deleteUser(String userID);
}
