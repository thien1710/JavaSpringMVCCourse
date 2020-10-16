package com.example.demo.service.impl;

import com.example.demo.utils.Configs;
import com.example.demo.utils.ErrorMessages;
import com.example.demo.exceptions.HandlingException;
import com.example.demo.model.customer.Customer;
import com.example.demo.model.customer.Customer_;
import com.example.demo.model.role.RoleName;
import com.example.demo.model.user.User;
import com.example.demo.model.user.User_;
import com.example.demo.payload.request.CustomerRequest;
import com.example.demo.payload.request.CustomerSearchCondition;
import com.example.demo.payload.request.SearchRequest;
import com.example.demo.payload.request.UserSearchCondition;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.payload.response.CustomerResponse;
import com.example.demo.reponsitory.CustomerRepository;
import com.example.demo.reponsitory.UserRepository;
import com.example.demo.service.CustomerService;
import com.example.demo.utils.EnumConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomerResponse addCustomer(CustomerRequest customerRequest, String currentUserUsername) {
        User user = userRepository.findByUsername(currentUserUsername);
        if (user == null) throw new UsernameNotFoundException(currentUserUsername + ErrorMessages.NOT_FOUND.getErrorMessage());

        Customer customer = new Customer();
//        customer.setCustomerName(customerRequest.getCustomerName());
        customer.setUser(user);
        BeanUtils.copyProperties(customerRequest, customer);

        Customer newCustomer = customerRepository.save(customer);

        CustomerResponse customerResponse = new CustomerResponse();

        customerResponse.setCustomerName(newCustomer.getCustomerName());

        return customerResponse;
    }

    @Override
    public Customer updateCustomer(Long customerId, CustomerRequest customerRequest, Authentication authentication) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new HandlingException(HttpStatus.NOT_FOUND,
                        EnumConstants.CUSTOMER.getEnumConstants() + ErrorMessages.NOT_FOUND_WITH.getErrorMessage()
                                + EnumConstants.ID.getEnumConstants() + EnumConstants.EQUAL.getEnumConstants() + customerId));

        if (customer.getUser().getUsername().equals(authentication.getName())
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + RoleName.ADMIN.toString()))
        ) {
            customer.setCustomerName(customerRequest.getCustomerName());
            Customer updatedCustomer = customerRepository.save(customer);
            return updatedCustomer;
        }

        throw new HandlingException(HttpStatus.UNAUTHORIZED,
                ErrorMessages.YOU_DON_T_HAVE_PERMISSION_TO.getErrorMessage() + EnumConstants.UPDATE.getEnumConstants() + EnumConstants.THIS_PROJECT.getEnumConstants());
    }

    @Override
    public ApiResponse deleteCustomer(Long customerId, Authentication authentication) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new HandlingException(HttpStatus.NOT_FOUND,
                        EnumConstants.CUSTOMER.getEnumConstants() + ErrorMessages.NOT_FOUND_WITH.getErrorMessage() +
                                EnumConstants.ID.getEnumConstants() + EnumConstants.EQUAL.getEnumConstants() + customerId));
        if (customer.getUser().getUsername().equals(authentication.getName())
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + RoleName.ADMIN.toString()))) {
            customerRepository.deleteById(customerId);
            return new ApiResponse(Boolean.TRUE, "You successfully deleted post");
        }

        throw new HandlingException(HttpStatus.UNAUTHORIZED,
                ErrorMessages.YOU_DON_T_HAVE_PERMISSION_TO.getErrorMessage() + EnumConstants.DELETE.getEnumConstants() + EnumConstants.THIS_PROJECT.getEnumConstants());
    }

    @Override
    public List<Customer> getUsers(int page, int limit) {
        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<Customer> usersPage = customerRepository.findAll(pageableRequest);
        List<Customer> users = usersPage.getContent();

        return users;
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public Collection<Customer> searchCustomers(SearchRequest searchRequest, int page) {

        if (searchRequest == null) {
            throw new HandlingException(HttpStatus.NOT_FOUND,
                    EnumConstants.SEARCH_REQUEST.getEnumConstants() + ErrorMessages.NOT_FOUND.getErrorMessage());
        }

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        Root<Customer> customerRoot = query.from(Customer.class);
        Join<Customer, User> customerUserJoin = customerRoot.join(Customer_.user);

        UserSearchCondition userSearchCondition = searchRequest.getSearchCondition().getUserSearchCondition();
        CustomerSearchCondition customerSearchCondition = searchRequest.getSearchCondition().getCustomerSearchCondition();

        Predicate hasDefault = builder.isTrue(builder.literal(true));

        /**
         * Customer condition
         */
        if (customerSearchCondition == null) {
            throw new HandlingException(HttpStatus.NOT_FOUND,
                    EnumConstants.USER_CONDITION.getEnumConstants() +ErrorMessages.IS_MANDATORY.getErrorMessage());
        }
        Predicate hasCustomerId = builder.equal(customerRoot.get(Customer_.id), customerSearchCondition.getId());
        Predicate hasCustomerName = builder.like(customerRoot.get(Customer_.customerName), "%" + customerSearchCondition.getCustomerName() + "%");
        Predicate hasCustomerPhone = builder.equal(customerRoot.get(Customer_.phone), customerSearchCondition.getPhone());
        Predicate hasCustomerEmail = builder.equal(customerRoot.get(Customer_.email), customerSearchCondition.getEmail());
        Predicate hasCustomerAddress = builder.like(customerRoot.get(Customer_.address), "%" + customerSearchCondition.getAddress() + "%");

        /**
         * User condition
         */
        if (userSearchCondition == null) {
            throw new HandlingException(HttpStatus.NOT_FOUND,
                    EnumConstants.CUSTOMER_CONDITION.getEnumConstants() + ErrorMessages.IS_MANDATORY.getErrorMessage());
        }
        Predicate hasId = builder.equal(customerUserJoin.get(User_.id), userSearchCondition.getId());
        Predicate hasIdHash = builder.equal(customerUserJoin.get(User_.userIdHash), userSearchCondition.getUserIdHash());
        Predicate hasFirstname = builder.like(customerUserJoin.get(User_.firstName), "%" + userSearchCondition.getFirstName() + "%");
        Predicate hasLastname = builder.like(customerUserJoin.get(User_.lastName), "%" + userSearchCondition.getLastName() + "%");
        Predicate hasUsername = builder.like(customerUserJoin.get(User_.username), "%" + userSearchCondition.getUsername() + "%");
        Predicate hasEmail = builder.equal(customerUserJoin.get(User_.email), userSearchCondition.getEmail());
        Predicate hasAddress = builder.like(customerUserJoin.get(User_.address), "%" + userSearchCondition.getAddress() + "%");
        Predicate hasPhone = builder.equal(customerUserJoin.get(User_.phone), userSearchCondition.getPhone());
        Predicate hasSalaryNum = builder.greaterThanOrEqualTo(customerUserJoin.get(User_.salaryNum), userSearchCondition.getSalaryNum());

        Predicate condition = builder.and(hasDefault);

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

        query.select(customerRoot).where(condition);
        return em.createQuery(query)
                .setFirstResult((page - 1) * Integer.parseInt(Configs.PAGING.USER.LIMIT))
                .setMaxResults(Integer.parseInt(Configs.PAGING.USER.LIMIT))
                .getResultList();
    }

}
