package com.example.demo.service;

import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.payload.request.SearchRequest;
import com.example.demo.payload.request.SetRolesRequest;
import com.example.demo.payload.request.UserAddRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.payload.response.UserResponse;

import java.util.Collection;
import java.util.List;

public interface UserService {

    User getCurrentUser(String username);

    ApiResponse addUser(UserAddRequest userAddResquest);

    UserResponse getUser(String id);

//    User getUserById(long id);

    User updateUser(User newUser, String username);

    ApiResponse deleteUser(long id);

    ApiResponse setRoles(SetRolesRequest setRolesRequest, long id);

    String requestPasswordForgot(String email);

    String requestPasswordReset(String token, String password);

    List<User> getUsersFilter(String fn);

    List<Project> getUsersFilterProject(String fn, long input2);

    User getUserById(String usename);

    Collection<User> searchUsers(SearchRequest searchRequest, int page);
}