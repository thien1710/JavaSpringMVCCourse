package com.example.demo.service.impl;

import com.example.demo.config.Configs;
import com.example.demo.config.ErrorMessages;
import com.example.demo.exceptions.AppException;
import com.example.demo.exceptions.BlogapiException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.customer.Customer;
import com.example.demo.model.project.Project;
import com.example.demo.model.role.RoleName;
import com.example.demo.model.user.User;
import com.example.demo.payload.request.ProjectRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.reponsitory.CustomerRepository;
import com.example.demo.reponsitory.ProjectRepository;
import com.example.demo.reponsitory.UserRepository;
import com.example.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final String PROJECT_DOES_NOT_BELONG_TO_CUSTOMER = "Project does not belong to customer";

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Project addProject(ProjectRequest projectRequest, Long customerId, Authentication authentication) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(Configs.AppConstant.CUSTOMER + " " + Configs.AppConstant.ID + " " + customerId + "not found"));
        User user = userRepository.findByUsername(authentication.getName());
        if (user == null) throw new UsernameNotFoundException(authentication.getName() + " not found");
        Project project = new Project();
        project.setUser(user);
        project.setCustomer(customer);
        project.setProjectName(projectRequest.getProjectName());

        Project newProject = projectRepository.save(project);

        return newProject;
    }

    @Override
    public Project updateProject(Long customerId, Long id, ProjectRequest projectRequest, Authentication authentication) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BlogapiException(HttpStatus.NOT_FOUND,
                        String.format("%s not found with %s: '%s'", Configs.AppConstant.CUSTOMER, Configs.AppConstant.ID, customerId)));
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BlogapiException(HttpStatus.NOT_FOUND,
                        String.format("%s not found with %s: '%s'", Configs.AppConstant.PROJECT, Configs.AppConstant.ID, id)));

        if (!project.getCustomer().getId().equals(customer.getId())){
            throw new BlogapiException(HttpStatus.BAD_REQUEST, PROJECT_DOES_NOT_BELONG_TO_CUSTOMER);
        }

        if (project.getUser().getUsername().equals(authentication.getName())
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + RoleName.ADMIN.toString()))
        ) {
            project.setProjectName(projectRequest.getProjectName());
            Project updatedProject = projectRepository.save(project);
            return updatedProject;
        }

        throw new BlogapiException(HttpStatus.UNAUTHORIZED,
                ErrorMessages.YOU_DON_T_HAVE_PERMISSION_TO.getErrorMessage() + "update" + ErrorMessages.THIS_PROJECT);
    }

    @Override
    public ApiResponse deleteProject(Long customerId, Long id, Authentication authentication) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BlogapiException(HttpStatus.NOT_FOUND,
                        String.format("%s not found with %s: '%s'", Configs.AppConstant.CUSTOMER, Configs.AppConstant.ID, id)));
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BlogapiException(HttpStatus.NOT_FOUND,
                        String.format("%s not found with %s: '%s'", Configs.AppConstant.PROJECT, Configs.AppConstant.ID, id)));

        if (!project.getCustomer().getId().equals(customer.getId())){
            throw new BlogapiException(HttpStatus.BAD_REQUEST, PROJECT_DOES_NOT_BELONG_TO_CUSTOMER);
        }

        if (project.getUser().getUsername().equals(authentication.getName())
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + RoleName.ADMIN.toString()))
        ) {
            projectRepository.deleteById(project.getId());
            return new ApiResponse(Boolean.TRUE, "You successfully deleted comment");
        }

        throw new BlogapiException(HttpStatus.UNAUTHORIZED,
                ErrorMessages.YOU_DON_T_HAVE_PERMISSION_TO.getErrorMessage() + "delete" + ErrorMessages.THIS_PROJECT);
    }
}
