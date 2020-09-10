package com.example.demo.controller;

import javax.validation.Valid;

import com.example.demo.config.Configs;
import com.example.demo.model.project.Project;
import com.example.demo.payload.request.ProjectRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.security.IAuthenticationFacade;
import com.example.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Configs.URL.PROJECT.PROJECTS)
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

//    @GetMapping
//    public ResponseEntity<PagedResponse<Comment>> getAllComments(@PathVariable(name = "postId") Long postId,
//                                                                 @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
//                                                                 @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
//
//        PagedResponse<Comment> allComments = commentService.getAllComments(postId, page, size);
//
//        return new ResponseEntity<PagedResponse<Comment>>(allComments, HttpStatus.OK);
//    }
//
    @PostMapping
    public ResponseEntity<Project> addProject(@Valid @RequestBody ProjectRequest projectRequest,
                                              @PathVariable(name = "customerId") Long customerId) {
        Authentication authentication = authenticationFacade.getAuthentication();

        String currentUserEmail = authentication.getName();

        Project newProject = projectService.addProject(projectRequest, customerId, currentUserEmail);

        return new ResponseEntity<Project>(newProject, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable(name = "customerId") Long customerId,
                                                 @PathVariable(name = "id") Long id, @Valid @RequestBody ProjectRequest projectRequest
                                                 ) {

        Authentication authentication = authenticationFacade.getAuthentication();

        String currentUserEmail = authentication.getName();

        Project updatedProject = projectService.updateProject(customerId, id, projectRequest, currentUserEmail);

        return new ResponseEntity<Project>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable(name = "customerId") Long customerId,
                                                     @PathVariable(name = "id") Long id) {

        Authentication authentication = authenticationFacade.getAuthentication();

        String currentUserEmail = authentication.getName();

        ApiResponse response = projectService.deleteProject(customerId, id, currentUserEmail);

        HttpStatus status = response.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<ApiResponse>(response, status);
    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Comment> getComment(@PathVariable(name = "postId") Long postId,
//                                              @PathVariable(name = "id") Long id) {
//        Comment comment = commentService.getComment(postId, id);
//
//        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
//    }
//
//
//
//

}
