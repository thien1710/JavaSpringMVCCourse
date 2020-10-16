package com.example.demo.service.impl;

import com.example.demo.config.TokenProvider;
import com.example.demo.exceptions.HandlingException;
import com.example.demo.model.project.Project;
import com.example.demo.model.resetpasswordentity.ResetPasswordEntity;
import com.example.demo.model.role.Role;
import com.example.demo.model.role.RoleName;
import com.example.demo.model.user.User;
import com.example.demo.model.user.User_;
import com.example.demo.payload.request.SearchRequest;
import com.example.demo.payload.request.SetRolesRequest;
import com.example.demo.payload.request.UserAddRequest;
import com.example.demo.payload.request.UserSearchCondition;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.payload.response.ResponseOperationName;
import com.example.demo.payload.response.UserAddResponse;
import com.example.demo.payload.response.UserResponse;
import com.example.demo.reponsitory.DepartmentRepository;
import com.example.demo.reponsitory.ResetPasswordRepository;
import com.example.demo.reponsitory.RoleRepository;
import com.example.demo.reponsitory.UserRepository;
import com.example.demo.security.SecurityConstants;
import com.example.demo.service.UserService;
import com.example.demo.utils.Configs;
import com.example.demo.utils.EnumConstants;
import com.example.demo.utils.ErrorMessages;
import com.example.demo.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    ResetPasswordRepository resetPasswordRepository;

    @Autowired
    Utils utils;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(ErrorMessages.INVALID_USERNAME_OR_PASSWORD.getErrorMessage());
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    @Override
    public User getCurrentUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username + ErrorMessages.NOT_FOUND);
        return user;
    }

    @Override
    public ApiResponse addUser(UserAddRequest userAddRequest) {
        User user = new User();
        if (userRepository.existsByUsername(userAddRequest.getUsername())) {
            throw new HandlingException(HttpStatus.BAD_REQUEST,
                    EnumConstants.USER_NAME.getEnumConstants() + ErrorMessages.IS_ALREADY_TAKEN.getErrorMessage());
        }

        if (userRepository.existsByEmail(userAddRequest.getEmail())) {
            throw new HandlingException(HttpStatus.BAD_REQUEST,
                    EnumConstants.EMAIL.getEnumConstants() + ErrorMessages.IS_ALREADY_TAKEN.getErrorMessage());
        }

        Set<Role> roles = new HashSet<>();

        //Convert Set to List
        List<Role> list = new ArrayList<>();
        list.addAll(userAddRequest.getRole());

        for (int i = 0; i < userAddRequest.getRole().size(); i++) {
            roles.add(
                    roleRepository.findByName(list.get(i).getName())
                            .orElseThrow(() -> new HandlingException(HttpStatus.NOT_FOUND,
                                    EnumConstants.USER_ROLE.getEnumConstants() + ErrorMessages.NOT_FOUND.getErrorMessage())));
        }

        roles.add(
                roleRepository.findByName(RoleName.USER).orElseThrow(() -> new HandlingException(HttpStatus.NOT_FOUND,
                        EnumConstants.USER_ROLE.getEnumConstants() + ErrorMessages.NOT_FOUND.getErrorMessage())));

        user.setRoles(roles);

        String publicUserId = utils.generateUserId(Configs.USER_ID_LENGTH);

        user.setUserIdHash(publicUserId);

        userAddRequest.setPassword(bCryptPasswordEncoder.encode(userAddRequest.getPassword()));

        BeanUtils.copyProperties(userAddRequest, user);

        userRepository.save(user);

        UserAddResponse userAddResponse = new UserAddResponse();
        BeanUtils.copyProperties(user, userAddResponse);

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
    public User updateUser(User newUser, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username + ErrorMessages.NOT_FOUND.getErrorMessage());
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
        if (user == null) throw new HandlingException(HttpStatus.NOT_FOUND,
                EnumConstants.USER.getEnumConstants() + ErrorMessages.NOT_FOUND_WITH.getErrorMessage()
                        + EnumConstants.ID.getEnumConstants() + EnumConstants.EQUAL.getEnumConstants() + id);
        userRepository.deleteById(user.getId());
        return new ApiResponse(Boolean.TRUE, EnumConstants.DELETE.getEnumConstants() + ResponseOperationName.SUCCESSFUL);
    }

    @Override
    public ApiResponse setRoles(SetRolesRequest setRolesRequest, long id) {

        User user = userRepository.findById(id);
        if (user == null) throw new HandlingException(HttpStatus.NOT_FOUND,
                EnumConstants.USER.getEnumConstants() + ErrorMessages.NOT_FOUND_WITH.getErrorMessage()
                        + EnumConstants.ID.getEnumConstants() + EnumConstants.EQUAL.getEnumConstants() + id);

        Set<Role> roles = new HashSet<>();

        //Convert Set to List
        List<Role> list = new ArrayList<>();
        list.addAll(setRolesRequest.getRole());

        for (int i = 0; i < setRolesRequest.getRole().size(); i++) {
            roles.add(
                    roleRepository.findByName(list.get(i).getName())
                            .orElseThrow(() -> new HandlingException(HttpStatus.NOT_FOUND,
                                    EnumConstants.USER_ROLE.getEnumConstants() + ErrorMessages.NOT_FOUND.getErrorMessage())));
        }

        roles.add(
                roleRepository.findByName(RoleName.USER).orElseThrow(() -> new HandlingException(HttpStatus.NOT_FOUND,
                        EnumConstants.USER_ROLE.getEnumConstants() + ErrorMessages.NOT_FOUND.getErrorMessage())));

        user.setRoles(roles);

        userRepository.save(user);
        return new ApiResponse(Boolean.TRUE, ResponseOperationName.SET_ROLES_TO_USER_SUCCESSFUL.toString());
    }

    @Override
    public String requestPasswordForgot(String email) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new HandlingException(HttpStatus.NOT_FOUND,
                    EnumConstants.EMAIL.getEnumConstants() + ErrorMessages.NOT_FOUND.getErrorMessage());
        }

        String token = new TokenProvider().generatePasswordResetToken(user.getUsername());

        ResetPasswordEntity resetPasswordEntity = new ResetPasswordEntity();
        resetPasswordEntity.setToken(token);
        resetPasswordEntity.setUserDetails(user);
        resetPasswordEntity.setTokenCreationDate(LocalDateTime.now());
        resetPasswordRepository.save(resetPasswordEntity);

        return token;
    }

    @Override
    public String requestPasswordReset(String token, String password) {
        ResetPasswordEntity resetPasswordEntity = resetPasswordRepository.findByToken(token);
        if (resetPasswordEntity == null) {
            throw new HandlingException(HttpStatus.NOT_FOUND,
                    EnumConstants.TOKEN.getEnumConstants() + ErrorMessages.NOT_FOUND.getErrorMessage());
        }

        LocalDateTime localDateTime = resetPasswordEntity.getTokenCreationDate();

        if (isTokenExpired(localDateTime)) {
            return EnumConstants.TOKEN.getEnumConstants() + ResponseOperationName.EXPIRED;
        }

        User user = resetPasswordEntity.getUserDetails();

        resetPasswordEntity.setToken(null);
        resetPasswordEntity.setTokenCreationDate(null);
        resetPasswordRepository.save(resetPasswordEntity);

        User copyUser = userRepository.findByEmail(user.getEmail());
        copyUser.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(copyUser);

        return EnumConstants.UPDATE.getEnumConstants() + EnumConstants.PASSWORD + ResponseOperationName.SUCCESSFUL;
    }

    @Override
    public List<User> getUsersFilter(String fn) {
        List<User> users = userRepository.myCustomQuery(fn);

        return users;
    }

    @Override
    public List<Project> getUsersFilterProject(String fn, long input2) {
        List<Project> users = userRepository.myCustomQueryProject(fn, input2);

        return users;
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public User getUserById(String usename) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);

        Predicate condition = builder.equal(userRoot.get(User_.username), usename);

        query.select(userRoot).where(condition);

        return em.createQuery(query).getSingleResult();
    }

    @Override
    public Collection<User> searchUsers(SearchRequest searchRequest, int page) {

        if (searchRequest == null) {
            throw new HandlingException(HttpStatus.NOT_FOUND,
                    EnumConstants.SEARCH_REQUEST.getEnumConstants() + ErrorMessages.NOT_FOUND.getErrorMessage());
        }

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);

        UserSearchCondition userSearchCondition = searchRequest.getSearchCondition().getUserSearchCondition();

        /**
         * User condition
         */
        if (userSearchCondition == null) {
            throw new HandlingException(HttpStatus.NOT_FOUND,
                    EnumConstants.CUSTOMER_CONDITION.getEnumConstants() + ErrorMessages.IS_MANDATORY.getErrorMessage());
        }
        Predicate hasDefault = builder.isTrue(builder.literal(true));
        Predicate hasId = builder.equal(userRoot.get(User_.id), userSearchCondition.getId());
        Predicate hasIdHash = builder.equal(userRoot.get(User_.userIdHash), userSearchCondition.getUserIdHash());
        Predicate hasFirstname = builder.like(userRoot.get(User_.firstName), "%" + userSearchCondition.getFirstName() + "%");
        Predicate hasLastname = builder.like(userRoot.get(User_.lastName), "%" + userSearchCondition.getLastName() + "%");
        Predicate hasUsername = builder.like(userRoot.get(User_.username), "%" + userSearchCondition.getUsername() + "%");
        Predicate hasEmail = builder.equal(userRoot.get(User_.email), userSearchCondition.getEmail());
        Predicate hasAddress = builder.like(userRoot.get(User_.address), "%" + userSearchCondition.getAddress() + "%");
        Predicate hasPhone = builder.equal(userRoot.get(User_.phone), userSearchCondition.getPhone());
        Predicate hasSalaryNum = builder.greaterThanOrEqualTo(userRoot.get(User_.salaryNum), userSearchCondition.getSalaryNum());

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

        query.select(userRoot).where(condition);
        return em.createQuery(query)
                .setFirstResult((page - 1) * Integer.parseInt(Configs.PAGING.USER.LIMIT))
                .setMaxResults(Integer.parseInt(Configs.PAGING.USER.LIMIT))
                .getResultList();
    }

    /**
     * Check whether the created token expired or not.
     *
     * @param tokenCreationDate
     * @return true or false
     */
    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= SecurityConstants.PASSWORD_RESET_ACCESS_TOKEN_VALIDITY_SECONDS;
    }

    public static void nativeQuery(EntityManager em, String s) {
        System.out.printf("'%s'%n", s);
        Query query = em.createNativeQuery(s);
        List list = query.getResultList();
        for (Object o : list) {
            if (o instanceof Object[]) {
                System.out.println(Arrays.toString((Object[]) o));
            } else {
                System.out.println(o);
            }
        }
    }

}
