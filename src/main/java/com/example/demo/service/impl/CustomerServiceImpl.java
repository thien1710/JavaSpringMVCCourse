package com.example.demo.service.impl;

import com.example.demo.config.Configs;
import com.example.demo.config.ErrorMessages;
import com.example.demo.exceptions.BlogapiException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.UnauthorizedException;
import com.example.demo.model.customer.Customer;
import com.example.demo.model.role.RoleName;
import com.example.demo.model.user.User;
import com.example.demo.payload.request.CustomerRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.payload.response.CustomerResponse;
import com.example.demo.reponsitory.CustomerRepository;
import com.example.demo.reponsitory.UserRepository;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (user == null) throw new UsernameNotFoundException(currentUserUsername + " not found");

        Customer customer = new Customer();
        customer.setCutomerName(customerRequest.getCustomerName());
        customer.setUser(user);

        Customer newCustomer = customerRepository.save(customer);

        CustomerResponse customerResponse = new CustomerResponse();

        customerResponse.setCustomerName(newCustomer.getCutomerName());

        return customerResponse;
    }

    @Override
    public Customer updateCustomer(Long id, CustomerRequest customerRequest, Authentication authentication) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BlogapiException(HttpStatus.NOT_FOUND,
                        String.format("%s not found with %s: '%s'", Configs.AppConstant.CUSTOMER, Configs.AppConstant.ID, id)));

        if (customer.getUser().getUsername().equals(authentication.getName())
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + RoleName.ADMIN.toString()))
        ) {
            customer.setCutomerName(customerRequest.getCustomerName());
            Customer updatedCustomer = customerRepository.save(customer);
            return updatedCustomer;
        }

        throw new BlogapiException(HttpStatus.UNAUTHORIZED,
                ErrorMessages.YOU_DON_T_HAVE_PERMISSION_TO.getErrorMessage() + " update" + ErrorMessages.THIS_PROJECT.getErrorMessage());
    }

    @Override
    public ApiResponse deleteCustomer(Long id, Authentication authentication) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BlogapiException(HttpStatus.NOT_FOUND,
                        String.format("%s not found with %s: '%s'", Configs.AppConstant.CUSTOMER, Configs.AppConstant.ID, id)));
        if (customer.getUser().getUsername().equals(authentication.getName())
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + RoleName.ADMIN.toString()))) {
            customerRepository.deleteById(id);
            return new ApiResponse(Boolean.TRUE, "You successfully deleted post");
        }

        throw new BlogapiException(HttpStatus.UNAUTHORIZED,
                ErrorMessages.YOU_DON_T_HAVE_PERMISSION_TO.getErrorMessage() + " delete" + ErrorMessages.THIS_PROJECT.getErrorMessage());
    }

    @Override
    public List<Customer> getUsers(int page, int limit) {
        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<Customer> usersPage = customerRepository.findAll(pageableRequest);
        List<Customer> users = usersPage.getContent();

        return users;
    }

}
