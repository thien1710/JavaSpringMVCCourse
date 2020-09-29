package com.example.demo.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import com.example.demo.config.Configs;
import com.example.demo.config.TokenProvider;
import com.example.demo.exceptions.AppException;
import com.example.demo.exceptions.HandlingException;
import com.example.demo.model.department.Department;
import com.example.demo.model.project.Project;
import com.example.demo.model.resetpasswordentity.ResetPasswordEntity;
import com.example.demo.model.role.Role;
import com.example.demo.model.role.RoleName;
import com.example.demo.model.user.User;
import com.example.demo.model.user.User_;
import com.example.demo.payload.request.SearchRequest;
import com.example.demo.payload.request.UserAddResquest;
import com.example.demo.payload.request.UserSearchCondition;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.payload.response.UserAddResponse;
import com.example.demo.payload.response.UserResponse;
import com.example.demo.reponsitory.DepartmentRepository;
import com.example.demo.reponsitory.ResetPasswordRepository;
import com.example.demo.reponsitory.RoleRepository;
import com.example.demo.reponsitory.UserRepository;
import com.example.demo.security.SecurityConstants;
import com.example.demo.service.UserService;
import com.example.demo.shared.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.persistence.criteria.*;

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
        //return Arrays.asList(new SimpleGranauthoritiestedAuthority("ROLE_ADMIN"));
    }

    @Override
    public User getCurrentUser(String username) {

//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
//        try {
//            EntityManager em = emf.createEntityManager();
//            nativeQuery(em, "SHOW TABLES");
//            nativeQuery(em, "SHOW COLUMNS from Customer");
//            nativeQuery(em, "SHOW COLUMNS from Phones");
//            emf.close();
//        } finally {
//            emf.close();
//        }

        User user = userRepository.findByUsername(username);
//                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        if (user == null) throw new UsernameNotFoundException(username);
        Set<Role> role = user.getRoles();
        Iterator<Role> it = role.iterator();

//        while (it.hasNext()) {
//
//            Role element = it.next();
//            String test = element.getName().name();
//            System.out.println(element + "=====" + test);
//        }

        Role element = it.next();
        String test = element.getName().name();
        System.out.println(element + "=====" + test);

        return user;
    }

    @Override
    public ApiResponse addUser(UserAddResquest userAddResquest) {
        User user = new User();
        if (userRepository.existsByUsername(userAddResquest.getUsername())) {
            throw new HandlingException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        if (userRepository.existsByEmail(userAddResquest.getEmail())) {
            throw new HandlingException(HttpStatus.BAD_REQUEST, "Email is already taken");
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
            throw new HandlingException(HttpStatus.BAD_REQUEST, "Role not found");
        }

        user.setRoles(roles);

        String publicUserId = utils.generateUserId(Configs.USER_ID_LENGTH);

        user.setUserIdHash(publicUserId);

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

//    @Override
//    public User getUserById(long id) {
//        User user = userRepository.findById(id);
//
//        if (user == null)
//            throw new UsernameNotFoundException("Id not found");
//
//        return user;
//    }

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

    @Override
    public String requestPasswordForgot(String email) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new AppException("Email " + email + " not found");
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
            throw new AppException("Token: " + token + " not found");
        }

        LocalDateTime localDateTime = resetPasswordEntity.getTokenCreationDate();

        if (isTokenExpired(localDateTime)) {
            return "Token expired.";
        }

        User user = resetPasswordEntity.getUserDetails();

        resetPasswordEntity.setToken(null);
        resetPasswordEntity.setTokenCreationDate(null);
        resetPasswordRepository.save(resetPasswordEntity);

        User copyUser = userRepository.findByEmail(user.getEmail());
        copyUser.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(copyUser);

        return "Your password successfully updated.";
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
//        query.select(root).where(builder.like(root.get("username"), "leanne%"));
//        return em.createQuery(query).getSingleResult();
    }

    @Override
    public Collection<User> searchUsers(SearchRequest searchRequest) {

        if (searchRequest == null) {
            throw new AppException("searchRequest not found");
        }

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);

        UserSearchCondition userSearchCondition = searchRequest.getSearchCondition().getUserSearchCondition();

        /**
         * User condition
         */
        if (userSearchCondition == null) {
            throw new AppException("userSearchCondition is mandatory");
        }
        Predicate hasDefault = builder.isTrue(builder.literal(true));
        Predicate hasId = builder.greaterThanOrEqualTo(userRoot.get(User_.id), userSearchCondition.getId());
        Predicate hasIdHash = builder.equal(userRoot.get(User_.userIdHash), userSearchCondition.getUserIdHash());
        Predicate hasFirstname = builder.like(userRoot.get(User_.firstName),"%"+ userSearchCondition.getFirstName() +"%");
        Predicate hasLastname = builder.like(userRoot.get(User_.lastName),"%"+ userSearchCondition.getLastName() +"%");
        Predicate hasUsername = builder.like(userRoot.get(User_.username),"%"+ userSearchCondition.getUsername() +"%");
        Predicate hasEmail = builder.equal(userRoot.get(User_.email), userSearchCondition.getEmail());
        Predicate hasAddress = builder.like(userRoot.get(User_.address),"%"+ userSearchCondition.getAddress() +"%");
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
        return em.createQuery(query).getResultList();
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
