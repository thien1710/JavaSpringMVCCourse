package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.demo.exceptions.AppException;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.department.Department;
import com.example.demo.model.role.Role;
import com.example.demo.model.role.RoleName;
import com.example.demo.model.user.User;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.reponsitory.DepartmentRepository;
import com.example.demo.reponsitory.RoleRepository;
import com.example.demo.reponsitory.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    //    @Autowired
//    private PostRepository postRepository;
//
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            //authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
        //return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
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
    public User addUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Username is already taken");
            throw new BadRequestException(apiResponse);
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Email is already taken");
            throw new BadRequestException(apiResponse);
        }

        Set<Role> roles = new HashSet<>();
//        List<Role> roles = new ArrayList<>();
        roles.add(
                roleRepository.findByName(RoleName.ADMIN).orElseThrow(() -> new AppException("User role not set")));
        user.setRoles(roles);

        List<Department> departments = new ArrayList<>();
        departments.add(departmentRepository.findByDepartmentName("Etown1").orElseThrow(() -> new AppException("User role not set")));
        user.setDepartments(departments);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User result = userRepository.save(user);

        return result;
    }

    @Override
    public UserDto getUser(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) throw new UsernameNotFoundException(email);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(user, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserById(long id) {
        UserDto returnValue = new UserDto();
        User user = userRepository.findById(id);

        if (user == null)
            throw new UsernameNotFoundException("id");

        BeanUtils.copyProperties(user, returnValue);
        return returnValue;
    }

    @Override
    public User updateUser(User newUser, String username) {
        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username))
        ;
        if (user == null) throw new UsernameNotFoundException(username);
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
    public ApiResponse deleteUser(String username) {
        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", username))
                ;
        if (user == null) throw new UsernameNotFoundException(username);
        userRepository.deleteById(user.getId());
        return new ApiResponse(Boolean.TRUE, "You successfully deleted profile of: " + username);
    }
//
//    @Override
//    public ApiResponse giveAdmin(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
//        List<Role> roles = new ArrayList<>();
//        roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN)
//                .orElseThrow(() -> new AppException("User role not set")));
//        roles.add(
//                roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("User role not set")));
//        user.setRoles(roles);
//        userRepository.save(user);
//        return new ApiResponse(Boolean.TRUE, "You gave ADMIN role to user: " + username);
//    }
//
//    @Override
//    public ApiResponse removeAdmin(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
//        List<Role> roles = new ArrayList<>();
//        roles.add(
//                roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("User role not set")));
//        user.setRoles(roles);
//        userRepository.save(user);
//        return new ApiResponse(Boolean.TRUE, "You took ADMIN role from user: " + username);
//    }
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
