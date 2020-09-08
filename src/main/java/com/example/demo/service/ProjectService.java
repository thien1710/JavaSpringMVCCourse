package com.example.demo.service;

import com.example.demo.model.project.Project;
import com.example.demo.payload.request.ProjectRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.security.UserPrincipal;

public interface ProjectService {
//    PagedResponse<Comment> getAllComments(Long postId, int page, int size);
//
    Project addProject(ProjectRequest projectRequest, Long customerId, String currentUserEmail);
//
//    Comment getComment(Long postId, Long id);
//
    Project updateProject(Long customerId, Long id, ProjectRequest projectRequest, String currentUserEmail);
//
    ApiResponse deleteProject(Long customerId, Long id, String currentUserEmail);
}
