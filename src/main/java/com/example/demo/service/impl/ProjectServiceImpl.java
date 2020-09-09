package com.example.demo.service.impl;

import com.example.demo.exceptions.BlogapiException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.customer.Customer;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.payload.request.ProjectRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.reponsitory.CustomerRepository;
import com.example.demo.reponsitory.ProjectRepository;
import com.example.demo.reponsitory.UserRepository;
import com.example.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    private static final String THIS_PROJECT = " this project";

    private static final String YOU_DON_T_HAVE_PERMISSION_TO = "You don't have permission to ";

    private static final String ID_STR = "id";

    private static final String COMMENT_STR = "Comment";

    private static final String Customer_STR = "Customer";

    private static final String PROJECT_DOES_NOT_BELONG_TO_CUSTOMER = "Project does not belong to customer";

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    //    @Override
//    public PagedResponse<Comment> getAllComments(Long postId, int page, int size) {
//        AppUtils.validatePageNumberAndSize(page, size);
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
//
//        Page<Comment> comments = commentRepository.findByPostId(postId, pageable);
//
//        return new PagedResponse<Comment>(comments.getContent(), comments.getNumber(), comments.getSize(),
//                comments.getTotalElements(), comments.getTotalPages(), comments.isLast());
//    }
//
    @Override
    public Project addProject(ProjectRequest projectRequest, Long customerId, String currentUserEmail) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(Customer_STR, ID_STR, customerId));
        User user = userRepository.findByEmail(currentUserEmail);
        if (user == null) throw new UsernameNotFoundException(currentUserEmail);
        Project project = new Project();
        project.setUser(user);
        project.setCustomer(customer);
        project.setProjectName(projectRequest.getProjectName());

        Project newProject = projectRepository.save(project);

        return newProject;
    }

    @Override
    public Project updateProject(Long customerId, Long id, ProjectRequest projectRequest, String currentUserEmail) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(Customer_STR, ID_STR, customerId));
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT_STR, ID_STR, id));

        if (!project.getCustomer().getId().equals(customer.getId())){
            throw new BlogapiException(HttpStatus.BAD_REQUEST, PROJECT_DOES_NOT_BELONG_TO_CUSTOMER);
        }

        if (project.getUser().getEmail().equals(currentUserEmail)
//        || urrentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))
        ) {
            project.setProjectName(projectRequest.getProjectName());
            Project updatedProject = projectRepository.save(project);
            return updatedProject;
        }

        throw new BlogapiException(HttpStatus.UNAUTHORIZED, YOU_DON_T_HAVE_PERMISSION_TO + "update" + THIS_PROJECT);
    }

    @Override
    public ApiResponse deleteProject(Long customerId, Long id, String currentUserEmail) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(Customer_STR, ID_STR, customerId));
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT_STR, ID_STR, id));

        if (!project.getCustomer().getId().equals(customer.getId())){
            throw new BlogapiException(HttpStatus.BAD_REQUEST, PROJECT_DOES_NOT_BELONG_TO_CUSTOMER);
        }

        if (project.getUser().getEmail().equals(currentUserEmail)
//        || urrentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))
        ) {
            projectRepository.deleteById(project.getId());
            return new ApiResponse(Boolean.TRUE, "You successfully deleted comment");
        }

        throw new BlogapiException(HttpStatus.UNAUTHORIZED, YOU_DON_T_HAVE_PERMISSION_TO + "delete" + THIS_PROJECT);
    }
//
//    @Override
//    public Comment getComment(Long postId, Long id) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new ResourceNotFoundException(POST_STR, ID_STR, postId));
//        Comment comment = commentRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException(COMMENT_STR, ID_STR, id));
//        if (comment.getPost().getId().equals(post.getId())) {
//            return comment;
//        }
//
//        throw new BlogapiException(HttpStatus.BAD_REQUEST, COMMENT_DOES_NOT_BELONG_TO_POST);
//    }
//

//
//
}
