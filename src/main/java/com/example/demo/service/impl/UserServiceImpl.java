package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.demo.exceptions.AppException;
import com.example.demo.exceptions.BlogapiException;
import com.example.demo.model.department.Department;
import com.example.demo.model.role.Role;
import com.example.demo.model.role.RoleName;
import com.example.demo.model.user.User;
import com.example.demo.payload.request.UserAddResquest;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.payload.response.UserAddResponse;
import com.example.demo.payload.response.UserResponse;
import com.example.demo.reponsitory.DepartmentRepository;
import com.example.demo.reponsitory.RoleRepository;
import com.example.demo.reponsitory.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
//        return new CurrentUser(user);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            //authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
        //return Arrays.asList(new SimpleGranauthoritiestedAuthority("ROLE_ADMIN"));
    }

//    @Override
//    public UserSummary getCurrentUser(UserPrincipal currentUser) {
//        return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getFirstName(),
//                currentUser.getLastName());
//    }
//
//    @Override
//    public UserIdentityAvailability checkUsernameAvailability(String username) {
//        Boolean isAvailable = !userRepository.existsByUsername(username);
//        return new UserIdentityAvailability(isAvailable);
//    }
//
//    @Override
//    public UserIdentityAvailability checkEmailAvailability(String email) {
//        Boolean isAvailable = !userRepository.existsByEmail(email);
//        return new UserIdentityAvailability(isAvailable);
//    }
//
//    @Override
//    public UserProfile getUserProfile(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
//
//        Long postCount = postRepository.countByCreatedBy(user.getId());
//
//        return new UserProfile(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(),
//                user.getCreatedAt(), user.getEmail(), user.getAddress(), user.getPhone(), user.getWebsite(),
//                user.getCompany(), postCount);
//    }

    @Override
    public User getCurrentUser(String username) {
        User user = userRepository.findByUsername(username);
//                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        if (user == null) throw new UsernameNotFoundException(username);

        return user;
    }

    @Override
    public ApiResponse addUser(UserAddResquest userAddResquest) {
        if (userRepository.existsByUsername(userAddResquest.getUsername())) {
            throw new BlogapiException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        if (userRepository.existsByEmail(userAddResquest.getEmail())) {
            throw new BlogapiException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }

        Set<Role> roles = new HashSet<>();
        if (userAddResquest.getRole() == null || userAddResquest.getRole().equals("") || userAddResquest.getRole().equals("USER")) {
            roles.add(
                    roleRepository.findByName(RoleName.USER).orElseThrow(() -> new AppException("User role not set")));
        } else if (userAddResquest.getRole().equals("ADMIN")) {
            roles.add(roleRepository.findByName(RoleName.ADMIN)
                    .orElseThrow(() -> new AppException("User role not set")));
            roles.add(roleRepository.findByName(RoleName.USER)
                    .orElseThrow(() -> new AppException("User role not set")));
        } else {
            throw new BlogapiException(HttpStatus.BAD_REQUEST, "Role not found");
        }

        User user = new User();
        user.setRoles(roles);


        List<Department> listDepartments = new ArrayList<>();
        Department department = departmentRepository.findById(userAddResquest.getDepartmentId().longValue());
        if (department == null) throw new AppException("departmentId not found");

        listDepartments.add(department);

        user.setDepartments(listDepartments);

        userAddResquest.setPassword(bCryptPasswordEncoder.encode(userAddResquest.getPassword()));

        BeanUtils.copyProperties(userAddResquest, user);

        userRepository.save(user);

        UserAddResponse userAddResponse = new UserAddResponse();
        BeanUtils.copyProperties(user, userAddResponse);

//        return userAddResponse;
        return new ApiResponse(Boolean.TRUE, "User registered successfully");
    }

    @Override
    public UserResponse getUser(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) throw new UsernameNotFoundException(email);

        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }

    @Override
    public User getUserById(long id) {
        User user = userRepository.findById(id);

        if (user == null)
            throw new UsernameNotFoundException("Id not found");

        return user;
    }

    @Override
    public User updateUser(User newUser, String username) {
        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username))
                ;
        if (user == null) throw new UsernameNotFoundException(username + " not found");
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setUsername(newUser.getUsername());

        user.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

        user.setAddress(newUser.getAddress());
        user.setPhone(newUser.getPhone());
        user.setWebsite(newUser.getWebsite());
        user.setSalaryNum(newUser.getSalaryNum());

        User updatedUser = userRepository.save(user);
        return updatedUser;

    }

    @Override
    public ApiResponse deleteUser(long id) {
        User user = userRepository.findById(id);
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", username))
                ;
        if (user == null) throw new UsernameNotFoundException("User Id: " + id + " not found");
        userRepository.deleteById(user.getId());
        return new ApiResponse(Boolean.TRUE, "You successfully deleted profile of: " + id);
    }

    @Override
    public ApiResponse giveAdmin(long id) {
        User user = userRepository.findById(id);
        if (user == null) throw new UsernameNotFoundException("User Id: " + id + " not found");

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(RoleName.ADMIN)
                .orElseThrow(() -> new AppException("User role not set")));
        roles.add(
                roleRepository.findByName(RoleName.USER).orElseThrow(() -> new AppException("User role not set")));
        user.setRoles(roles);
        userRepository.save(user);
        return new ApiResponse(Boolean.TRUE, "You gave ADMIN role to user: " + id);
    }

    @Override
    public ApiResponse removeAdmin(long id) {
        User user = userRepository.findById(id);
        if (user == null) throw new UsernameNotFoundException("User Id: " + id + " not found");
        Set<Role> roles = new HashSet<>();
        roles.add(
                roleRepository.findByName(RoleName.USER).orElseThrow(() -> new AppException("User role not set")));
        user.setRoles(roles);
        userRepository.save(user);
        return new ApiResponse(Boolean.TRUE, "You took ADMIN role from user: " + id);
    }
//
//    @Override
//    public UserProfile setOrUpdateInfo(UserPrincipal currentUser, InfoRequest infoRequest) {
//        User user = userRepository.findByUsername(currentUser.getUsername())
//                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getUsername()));
//        Geo geo = new Geo(infoRequest.getLat(), infoRequest.getLng());
//        Address address = new Address(infoRequest.getStreet(), infoRequest.getSuite(), infoRequest.getCity(),
//                infoRequest.getZipcode(), geo);
//        Company company = new Company(infoRequest.getCompanyName(), infoRequest.getCatchPhrase(), infoRequest.getBs());
//        if (user.getId().equals(currentUser.getId())
//                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
//            user.setAddress(address);
//            user.setCompany(company);
//            user.setWebsite(infoRequest.getWebsite());
//            user.setPhone(infoRequest.getPhone());
//            User updatedUser = userRepository.save(user);
//
//            Long postCount = postRepository.countByCreatedBy(updatedUser.getId());
//
//            UserProfile userProfile = new UserProfile(updatedUser.getId(), updatedUser.getUsername(),
//                    updatedUser.getFirstName(), updatedUser.getLastName(), updatedUser.getCreatedAt(),
//                    updatedUser.getEmail(), updatedUser.getAddress(), updatedUser.getPhone(), updatedUser.getWebsite(),
//                    updatedUser.getCompany(), postCount);
//            return userProfile;
//        }
//
//        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to update users profile", HttpStatus.FORBIDDEN);
//        throw new AccessDeniedException(apiResponse);
//    }


}
