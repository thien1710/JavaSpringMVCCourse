package com.example.demo.service;

import com.example.demo.model.customer.Customer;
import com.example.demo.payload.request.CustomerRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.payload.response.CustomerResponse;

public interface CustomerService {

//    PagedResponse<Post> getAllPosts(int page, int size);
//
//    PagedResponse<Post> getPostsByCreatedBy(String username, int page, int size);
//
//    PagedResponse<Post> getPostsByCategory(Long id, int page, int size);
//
//    PagedResponse<Post> getPostsByTag(Long id, int page, int size);
//
    Customer updateCustomer(Long id, CustomerRequest newPostRequest, String currentUserEmail);
//
    ApiResponse deleteCustomer(Long id, String currentUserEmail);
//
    CustomerResponse addCustomer(CustomerRequest postRequest, String currentUserEmail);
//
//    Post getPost(Long id);

}
