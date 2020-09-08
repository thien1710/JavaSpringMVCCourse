package com.example.demo.service.impl;

import static com.example.demo.config.AppConstants.POST;
import static com.example.demo.config.AppConstants.ID;


import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.UnauthorizedException;
import com.example.demo.model.customer.Customer;
import com.example.demo.model.user.User;
import com.example.demo.payload.request.CustomerRequest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.payload.response.CustomerResponse;
import com.example.demo.reponsitory.CustomerRepository;
import com.example.demo.reponsitory.UserRepository;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private TagRepository tagRepository;
//
//    @Override
//    public PagedResponse<Post> getAllPosts(int page, int size) {
//        validatePageNumberAndSize(page, size);
//
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
//
//        Page<Post> posts = postRepository.findAll(pageable);
//
//        List<Post> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();
//
//        return new PagedResponse<>(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
//                posts.getTotalPages(), posts.isLast());
//    }
//
//    @Override
//    public PagedResponse<Post> getPostsByCreatedBy(String username, int page, int size) {
//        validatePageNumberAndSize(page, size);
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new ResourceNotFoundException(USER, USERNAME, username));
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
//        Page<Post> posts = postRepository.findByCreatedBy(user.getId(), pageable);
//
//        List<Post> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();
//
//        return new PagedResponse<>(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
//                posts.getTotalPages(), posts.isLast());
//    }
//
//    @Override
//    public PagedResponse<Post> getPostsByCategory(Long id, int page, int size) {
//        AppUtils.validatePageNumberAndSize(page, size);
//        Category category = categoryRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, id));
//
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
//        Page<Post> posts = postRepository.findByCategory(category.getId(), pageable);
//
//        List<Post> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();
//
//        return new PagedResponse<>(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
//                posts.getTotalPages(), posts.isLast());
//    }
//
//    @Override
//    public PagedResponse<Post> getPostsByTag(Long id, int page, int size) {
//        AppUtils.validatePageNumberAndSize(page, size);
//
//        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(TAG, ID, id));
//
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
//
//        Page<Post> posts = postRepository.findByTags(Arrays.asList(tag), pageable);
//
//        List<Post> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();
//
//        return new PagedResponse<>(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
//                posts.getTotalPages(), posts.isLast());
//    }
//
    @Override
    public Customer updateCustomer(Long id, CustomerRequest customerRequest, String currentUserEmail) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(POST, ID, id));

        if (customer.getUser().getEmail().equals(currentUserEmail)) {
            customer.setCutomerName(customerRequest.getCustomerName());
            Customer updatedCustomer = customerRepository.save(customer);
            return updatedCustomer;
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to edit this post");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public ApiResponse deleteCustomer(Long id, String currentUserEmail) {
//        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(POST, ID, id));
//        if (post.getUser().getId().equals(currentUser.getId())
//                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
//            postRepository.deleteById(id);
//            return new ApiResponse(Boolean.TRUE, "You successfully deleted post");
//        }
//
//        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete this post");
//
//        throw new UnauthorizedException(apiResponse);
        return null;
    }
//
    @Override
    public CustomerResponse addCustomer(CustomerRequest customerRequest, String currentUserEmail) {
        User user = userRepository.findByEmail(currentUserEmail);
        if (user == null) throw new UsernameNotFoundException(currentUserEmail);
//        User user = userRepository.findById(currentUser.getId())
//                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

        Customer customer = new Customer();
        customer.setCutomerName(customerRequest.getCustomerName());
        customer.setUser(user);

        Customer newCustomer = customerRepository.save(customer);

        CustomerResponse customerResponse = new CustomerResponse();

        customerResponse.setCustomerName(newCustomer.getCutomerName());

        return customerResponse;
    }
//
//    @Override
//    public Post getPost(Long id) {
//        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(POST, ID, id));
//        return post;
//    }
//
//    private void validatePageNumberAndSize(int page, int size) {
//        if (page < 0) {
//            throw new BadRequestException("Page number cannot be less than zero.");
//        }
//
//        if (size < 0) {
//            throw new BadRequestException("Size number cannot be less than zero.");
//        }
//
//        if (size > AppConstants.MAX_PAGE_SIZE) {
//            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
//        }
//    }
}
