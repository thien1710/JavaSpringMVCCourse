package com.example.demo.service.impl;

import com.example.demo.config.Configs;
import com.example.demo.config.ErrorMessages;
import com.example.demo.exceptions.AppException;
import com.example.demo.exceptions.HandlingException;
import com.example.demo.model.customer.Customer;
import com.example.demo.model.customer.Customer_;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.Project_;
import com.example.demo.model.role.RoleName;
import com.example.demo.model.user.User;
import com.example.demo.model.user.User_;
import com.example.demo.payload.request.*;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.reponsitory.CustomerRepository;
import com.example.demo.reponsitory.ProjectRepository;
import com.example.demo.reponsitory.UserRepository;
import com.example.demo.service.ProjectService;
import com.example.demo.shared.EnumConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Collection;

@Service
public class ProjectServiceImpl implements ProjectService {

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
                .orElseThrow(() -> new HandlingException(HttpStatus.NOT_FOUND,
                        String.format("%s not found with %s: '%s'", Configs.AppConstant.CUSTOMER, Configs.AppConstant.ID, customerId)));
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new HandlingException(HttpStatus.NOT_FOUND,
                        String.format("%s not found with %s: '%s'", Configs.AppConstant.PROJECT, Configs.AppConstant.ID, id)));

        if (!project.getCustomer().getId().equals(customer.getId())){
            throw new HandlingException(HttpStatus.BAD_REQUEST, EnumConstants.PROJECT.getEnumConstants() + ErrorMessages.DOES_NOT_BELONG_TO + EnumConstants.CUSTOMER.getEnumConstants());
        }

        if (project.getUser().getUsername().equals(authentication.getName())
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + RoleName.ADMIN.toString()))
        ) {
            project.setProjectName(projectRequest.getProjectName());
            Project updatedProject = projectRepository.save(project);
            return updatedProject;
        }

        throw new HandlingException(HttpStatus.UNAUTHORIZED,
                ErrorMessages.YOU_DON_T_HAVE_PERMISSION_TO.getErrorMessage() + EnumConstants.UPDATE.getEnumConstants() + EnumConstants.PROJECT.getEnumConstants());
    }

    @Override
    public ApiResponse deleteProject(Long customerId, Long id, Authentication authentication) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new HandlingException(HttpStatus.NOT_FOUND,
                        String.format("%s not found with %s: '%s'", Configs.AppConstant.CUSTOMER, Configs.AppConstant.ID, id)));
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new HandlingException(HttpStatus.NOT_FOUND,
                        String.format("%s not found with %s: '%s'", Configs.AppConstant.PROJECT, Configs.AppConstant.ID, id)));

        if (!project.getCustomer().getId().equals(customer.getId())){
            throw new HandlingException(HttpStatus.BAD_REQUEST, EnumConstants.PROJECT.getEnumConstants() + ErrorMessages.DOES_NOT_BELONG_TO + EnumConstants.CUSTOMER.getEnumConstants());
        }

        if (project.getUser().getUsername().equals(authentication.getName())
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + RoleName.ADMIN.toString()))
        ) {
            projectRepository.deleteById(project.getId());
            return new ApiResponse(Boolean.TRUE, "You successfully deleted comment");
        }

        throw new HandlingException(HttpStatus.UNAUTHORIZED,
                ErrorMessages.YOU_DON_T_HAVE_PERMISSION_TO.getErrorMessage() + EnumConstants.UPDATE.getEnumConstants() + EnumConstants.PROJECT.getEnumConstants());
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public Collection<Project> searchProject(SearchRequest searchRequest) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Project> query = builder.createQuery(Project.class);

        //User
        Root<User> userRoot = query.from(User.class);

        //Customer
        Root<Customer> customerRoot = query.from(Customer.class);
        Join<Customer, User> customerUserJoin = customerRoot.join(Customer_.user);

        Root<Project> project = query.from(Project.class);
        Join<Project, Customer> customer = project.join(Project_.customer);
        Join<Project, User> user = project.join(Project_.user);

        UserSearchCondition userSearchCondition = searchRequest.getSearchCondition().getUserSearchCondition();
        CustomerSearchCondition customerSearchCondition = searchRequest.getSearchCondition().getCustomerSearchCondition();
        ProjectSearchCondition projectSearchCondition = searchRequest.getSearchCondition().getProjectSearchCondition();

        Predicate hasDefault = builder.isTrue(builder.literal(true));

        /**
         * Customer condition
         */
        if (customerSearchCondition == null) {
            throw new AppException("customerSearchCondition is mandatory");
        }
        Predicate hasCustomerId = builder.equal(customer.get(Customer_.id), customerSearchCondition.getId());
        Predicate hasCustomerName = builder.like(customer.get(Customer_.customerName), "%" + customerSearchCondition.getCustomerName() + "%");
        Predicate hasCustomerPhone = builder.equal(customer.get(Customer_.phone), customerSearchCondition.getPhone());
        Predicate hasCustomerEmail = builder.equal(customer.get(Customer_.email), customerSearchCondition.getEmail());
        Predicate hasCustomerAddress = builder.like(customer.get(Customer_.address), "%" + customerSearchCondition.getAddress() + "%");

        /**
         * User condition
         */
        if (userSearchCondition == null) {
            throw new AppException("userSearchCondition is mandatory");
        }
        Predicate hasId = builder.equal(user.get(User_.id), userSearchCondition.getId());
        Predicate hasIdHash = builder.equal(user.get(User_.userIdHash), userSearchCondition.getUserIdHash());
        Predicate hasFirstname = builder.like(user.get(User_.firstName), "%" + userSearchCondition.getFirstName() + "%");
        Predicate hasLastname = builder.like(user.get(User_.lastName), "%" + userSearchCondition.getLastName() + "%");
        Predicate hasUsername = builder.like(user.get(User_.username), "%" + userSearchCondition.getUsername() + "%");
        Predicate hasEmail = builder.equal(user.get(User_.email), userSearchCondition.getEmail());
        Predicate hasAddress = builder.like(user.get(User_.address), "%" + userSearchCondition.getAddress() + "%");
        Predicate hasPhone = builder.equal(user.get(User_.phone), userSearchCondition.getPhone());
        Predicate hasSalaryNum = builder.greaterThanOrEqualTo(user.get(User_.salaryNum), userSearchCondition.getSalaryNum());

        /**
         * Project condition
         */
        if (projectSearchCondition == null) {
            throw new AppException("userSearchCondition is mandatory");
        }
        Predicate hasProjectId = builder.equal(project.get(Project_.id), projectSearchCondition.getId());
        Predicate hasProjectName = builder.like(project.get(Project_.projectName), "%" + projectSearchCondition.getProjectName() + "%");
        Predicate hasStartTimeGt = builder.greaterThanOrEqualTo(project.get(Project_.startTime), projectSearchCondition.getStartTime());
        Predicate hasFinishTimeGt = builder.greaterThanOrEqualTo(project.get(Project_.finishtTime), projectSearchCondition.getFinishtTime());
        Predicate hasTotalTimeGt = builder.greaterThanOrEqualTo(project.get(Project_.totalTime), projectSearchCondition.getTotalTime());
        Predicate hasBudgetGt = builder.greaterThanOrEqualTo(project.get(Project_.budget), projectSearchCondition.getBudget());

        Predicate condition = builder.and(hasDefault);

        /**
         * User
         */
        if (userSearchCondition.getId() != null) {
            condition = builder.and(condition, hasId);
        }

        if (userSearchCondition.getUserIdHash() != null) {
            condition = builder.and(condition, hasIdHash);
        }

        if (userSearchCondition.getFirstName() != null) {
            condition = builder.and(condition, hasFirstname);
        }

        if (userSearchCondition.getLastName() != null) {
            condition = builder.and(condition, hasLastname);
        }

        if (userSearchCondition.getUsername() != null) {
            condition = builder.and(condition, hasUsername);
        }

        if (userSearchCondition.getEmail() != null) {
            condition = builder.and(condition, hasEmail);
        }

        if (userSearchCondition.getAddress() != null) {
            condition = builder.and(condition, hasAddress);
        }

        if (userSearchCondition.getPhone() != null) {
            condition = builder.and(condition, hasPhone);
        }

        if (userSearchCondition.getSalaryNum() != null) {
            condition = builder.and(condition, hasSalaryNum);
        }

        /**
         * Customer
         */
        if (customerSearchCondition.getId() != null) {
            condition = builder.and(condition, hasCustomerId);
        }

        if (customerSearchCondition.getAddress() != null) {
            condition = builder.and(condition, hasCustomerAddress);
        }

        if (customerSearchCondition.getCustomerName() != null) {
            condition = builder.and(condition, hasCustomerName);
        }

        if (customerSearchCondition.getEmail() != null) {
            condition = builder.and(condition, hasCustomerEmail);
        }

        if (customerSearchCondition.getPhone() != null) {
            condition = builder.and(condition, hasCustomerPhone);
        }

        /**
        /**
         * Project
         */
        if (projectSearchCondition.getId() != null) {
            condition = builder.and(condition, hasProjectId);
        }

        if (projectSearchCondition.getProjectName() != null) {
            condition = builder.and(condition, hasProjectName);
        }

        if (projectSearchCondition.getStartTime() != null) {
            condition = builder.and(condition, hasStartTimeGt);
        }

        if (projectSearchCondition.getFinishtTime() != null) {
            condition = builder.and(condition, hasFinishTimeGt);
        }

        if (projectSearchCondition.getTotalTime() != null) {
            condition = builder.and(condition, hasTotalTimeGt);
        }

        if (projectSearchCondition.getBudget() != null) {
            condition = builder.and(condition, hasBudgetGt);
        }

        query.select(project).where(condition);
        return em.createQuery(query).getResultList();
    }
}
