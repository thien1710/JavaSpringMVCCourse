package com.example.demo.service;

import com.example.demo.model.user.User;
import com.example.demo.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

//    UserSummary getCurrentUser(UserPrincipal currentUser);
//
//    UserIdentityAvailability checkUsernameAvailability(String username);
//
//    UserIdentityAvailability checkEmailAvailability(String email);
//
//    UserProfile getUserProfile(String username);

    User addUser(User user);

    UserDto getUser(String email);

//    User updateUser(User newUser, String username, UserPrincipal currentUser);
//
//    ApiResponse deleteUser(String username, UserPrincipal currentUser);
//
//    ApiResponse giveAdmin(String username);
//
//    ApiResponse removeAdmin(String username);
//
//    UserProfile setOrUpdateInfo(UserPrincipal currentUser, InfoRequest infoRequest);

}