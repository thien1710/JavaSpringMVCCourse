package com.example.demo.service;

import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.payload.request.UserAddResquest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.payload.response.UserAddResponse;
import com.example.demo.payload.response.UserResponse;
import com.example.demo.shared.dto.UserDto;

import java.util.List;

public interface UserService {

    User getCurrentUser(String username);

    ApiResponse addUser(UserAddResquest userAddResquest);

    UserResponse getUser(String id);

    User getUserById(long id);

    User updateUser(User newUser, String username);

    ApiResponse deleteUser(long id);

    ApiResponse giveAdmin(long id);

    ApiResponse removeAdmin(long id);

    String requestPasswordForgot(String email);

    String requestPasswordReset(String token, String password);

    List<User> getUsersFilter(String fn);

    List<Project> getUsersFilterProject(String fn, long input2);

}