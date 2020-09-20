package com.example.demo.service;

import com.example.demo.model.customer.Customer;
import com.example.demo.payload.request.CustomerRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.payload.response.CustomerResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface CustomerService {

    Customer updateCustomer(Long id, CustomerRequest newPostRequest, Authentication authentication);

    ApiResponse deleteCustomer(Long id, Authentication authentication);

    CustomerResponse addCustomer(CustomerRequest postRequest, String currentUserUsername);

    List<Customer> getUsers(int page, int limit);

}
