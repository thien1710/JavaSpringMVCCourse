package com.example.demo.controller;

import javax.validation.Valid;

import com.example.demo.config.Configs;
import com.example.demo.model.customer.Customer;
import com.example.demo.payload.request.CustomerRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.payload.response.CustomerResponse;
import com.example.demo.security.IAuthenticationFacade;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(Configs.URL.CUSTOMER.CUSTOMERS)
public class CustomerController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private CustomerService customerService;
//
//    @GetMapping
//    public ResponseEntity<PagedResponse<Post>> getAllPosts(
//            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
//            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
//        PagedResponse<Post> response = postService.getAllPosts(page, size);
//
//        return new ResponseEntity<PagedResponse<Post>>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/category/{id}")
//    public ResponseEntity<PagedResponse<Post>> getPostsByCategory(
//            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
//            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
//            @PathVariable(name = "id") Long id) {
//        PagedResponse<Post> response = postService.getPostsByCategory(id, page, size);
//
//        return new ResponseEntity<PagedResponse<Post>>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/tag/{id}")
//    public ResponseEntity<PagedResponse<Post>> getPostsByTag(
//            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
//            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
//            @PathVariable(name = "id") Long id) {
//        PagedResponse<Post> response = postService.getPostsByTag(id, page, size);
//
//        return new ResponseEntity<PagedResponse<Post>>(response, HttpStatus.OK);
//    }
//
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomerResponse> addCustomer(@Valid @RequestBody CustomerRequest customerRequest, Authentication authentication) {

        CustomerResponse postResponse = customerService.addCustomer(customerRequest, authentication.getName());

        return new ResponseEntity<CustomerResponse>(postResponse, HttpStatus.CREATED);
    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Post> getPost(@PathVariable(name = "id") Long id) {
//        Post post = postService.getPost(id);
//
//        return new ResponseEntity<Post>(post, HttpStatus.OK);
//    }
//
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(name = "id") Long id,
                                               @Valid @RequestBody CustomerRequest customerRequest,
                                               Authentication authentication) {

        Customer post = customerService.updateCustomer(id, customerRequest, authentication);

        return new ResponseEntity<Customer>(post, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable(name = "id") Long id, Authentication authentication) {
        ApiResponse apiResponse = customerService.deleteCustomer(id, authentication);

        HttpStatus status = apiResponse.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

}
