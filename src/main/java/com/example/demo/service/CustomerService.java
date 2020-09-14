package com.example.demo.service;

import com.example.demo.model.customer.Customer;
import com.example.demo.payload.request.CustomerRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.payload.response.CustomerResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface CustomerService {

//    PagedResponse<Post> getAllPosts(int page, int size);
//
//    PagedResponse<Post> getPostsByCreatedBy(String username, int page, int size);
//
//    PagedResponse<Post> getPostsByCategory(Long id, int page, int size);
//
//    PagedResponse<Post> getPostsByTag(Long id, int page, int size);
//
    Customer updateCustomer(Long id, CustomerRequest newPostRequest, Authentication authentication);
//
    ApiResponse deleteCustomer(Long id, Authentication authentication);
//
    CustomerResponse addCustomer(CustomerRequest postRequest, String currentUserUsername);
//
//    Post getPost(Long id);

}
