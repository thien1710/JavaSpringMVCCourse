package com.example.demo.controller;

import com.example.demo.model.customer.Customer;
import com.example.demo.payload.request.CustomerRequest;
import com.example.demo.payload.request.SearchRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.payload.response.CustomerResponse;
import com.example.demo.security.IAuthenticationFacade;
import com.example.demo.service.CustomerService;
import com.example.demo.utils.Configs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping(Configs.URL.CUSTOMER.CUSTOMERS)
public class CustomerController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADD_CUSTOMER')")
    public ResponseEntity<CustomerResponse> addCustomer(@Valid @RequestBody CustomerRequest customerRequest, Authentication authentication) {

        CustomerResponse postResponse = customerService.addCustomer(customerRequest, authentication.getName());

        return new ResponseEntity<CustomerResponse>(postResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('UPDATE_CUSTOMER')")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(name = "id") Long id,
                                               @Valid @RequestBody CustomerRequest customerRequest,
                                               Authentication authentication) {

        Customer post = customerService.updateCustomer(id, customerRequest, authentication);

        return new ResponseEntity<Customer>(post, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DELETE_CUSTOMER')")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable(name = "id") Long id, Authentication authentication) {
        ApiResponse apiResponse = customerService.deleteCustomer(id, authentication);

        HttpStatus status = apiResponse.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Customer> getUsers(@RequestParam(value = "page", defaultValue = Configs.PAGING.USER.PAGE) int page,
                                   @RequestParam(value = "limit", defaultValue = Configs.PAGING.USER.LIMIT) int limit) {
        List<Customer> returnValue = new ArrayList<>();
        int currentPage = page > 0 ? page - 1 : Integer.parseInt(Configs.PAGING.USER.PAGE);
        int currentLimit = limit > 0 ? limit : Integer.parseInt(Configs.PAGING.USER.LIMIT);
        List<Customer> users = customerService.getUsers(currentPage, currentLimit);

        return users;
    }

    @GetMapping(path = "/search",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Collection<Customer> searchCustomers(@RequestBody SearchRequest searchRequest,
                                                @RequestParam(value = "page", defaultValue = Configs.PAGING.USER.PAGE) int page) {
        int currentPage = page > 0 ? page : Integer.parseInt(Configs.PAGING.USER.PAGE);
        Collection<Customer> userCollection = customerService.searchCustomers(searchRequest, page);
        return userCollection;
    }

}
