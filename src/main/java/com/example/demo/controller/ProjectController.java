package com.example.demo.controller;

import javax.validation.Valid;

import com.example.demo.config.Configs;
import com.example.demo.model.customer.Customer;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.payload.request.ProjectRequest;
import com.example.demo.payload.request.SearchRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.security.IAuthenticationFacade;
import com.example.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(Configs.URL.PROJECT.PROJECTS)
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @PostMapping(Configs.URL.PROJECT.CUSTOMERID_PATH)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Project> addProject(@Valid @RequestBody ProjectRequest projectRequest,
                                              @PathVariable(name = "customerId") Long customerId, Authentication authentication) {
        Project newProject = projectService.addProject(projectRequest, customerId, authentication);

        return new ResponseEntity<Project>(newProject, HttpStatus.CREATED);
    }

    @PutMapping(Configs.URL.PROJECT.CUSTOMERID_PATH + "/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Project> updateProject(@PathVariable(name = "customerId") Long customerId,
                                                 @PathVariable(name = "id") Long id,
                                                 @Valid @RequestBody ProjectRequest projectRequest,
                                                 Authentication authentication
                                                 ) {

        Project updatedProject = projectService.updateProject(customerId, id, projectRequest, authentication);

        return new ResponseEntity<Project>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping(Configs.URL.PROJECT.CUSTOMERID_PATH + "/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable(name = "customerId") Long customerId,
                                                     @PathVariable(name = "id") Long id,
                                                     Authentication authentication) {

        ApiResponse response = projectService.deleteProject(customerId, id, authentication);

        HttpStatus status = response.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<ApiResponse>(response, status);
    }

    @GetMapping(path = "/projects/search",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Collection<Project> searchCustomers(@RequestBody SearchRequest searchRequest) {
        Collection<Project> projectCollection = projectService.searchProject(searchRequest);
        return projectCollection;
    }

}
