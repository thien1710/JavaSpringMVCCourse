package com.example.demo.service;

import com.example.demo.model.user.User;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

//    UserSummary getCurrentUser(UserPrincipal currentUser);
//
//    UserIdentityAvailability checkUsernameAvailability(String username);
//
//    UserIdentityAvailability checkEmailAvailability(String email);
//
//    UserProfile getUserProfile(String username);

    User addUser(User user);

    UserDto getUser(String email);

    UserDto getUserById(long id);

    User updateUser(User newUser, String username);

    ApiResponse deleteUser(String username);
//
//    ApiResponse giveAdmin(String username);
//
//    ApiResponse removeAdmin(String username);
//
//    UserProfile setOrUpdateInfo(UserPrincipal currentUser, InfoRequest infoRequest);

}