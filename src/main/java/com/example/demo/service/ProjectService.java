package com.example.demo.service;

import com.example.demo.model.project.Project;
import com.example.demo.payload.request.ProjectRequest;
import com.example.demo.payload.request.SearchRequest;
import com.example.demo.payload.response.ApiResponse;
import org.springframework.security.core.Authentication;

import java.util.Collection;

public interface ProjectService {

    Project addProject(ProjectRequest projectRequest, Long customerId, Authentication authentication);

    Project updateProject(Long customerId, Long id, ProjectRequest projectRequest, Authentication authentication);

    ApiResponse deleteProject(Long customerId, Long id, Authentication authentication);

    Collection<Project> searchProject(SearchRequest searchRequest, int page);
}
