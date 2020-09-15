package com.example.demo.service;

import com.example.demo.model.user.User;
import com.example.demo.payload.request.UserAddResquest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.payload.response.UserAddResponse;
import com.example.demo.payload.response.UserResponse;
import com.example.demo.shared.dto.UserDto;

public interface UserService {

//    UserSummary getCurrentUser(UserPrincipal currentUser);
//
//    UserIdentityAvailability checkUsernameAvailability(String username);
//
//    UserIdentityAvailability checkEmailAvailability(String email);
//
//    UserProfile getUserProfile(String username);

    User getCurrentUser(String username);

    ApiResponse addUser(UserAddResquest userAddResquest);

    UserResponse getUser(String id);

    User getUserById(long id);

    User updateUser(User newUser, String username);

    ApiResponse deleteUser(long id);

    ApiResponse giveAdmin(long id);

    ApiResponse removeAdmin(long id);
//
//    UserProfile setOrUpdateInfo(UserPrincipal currentUser, InfoRequest infoRequest);

}