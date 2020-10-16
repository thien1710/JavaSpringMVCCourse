package com.example.demo.controller;

import com.example.demo.config.TokenProvider;
import com.example.demo.exceptions.AppException;
import com.example.demo.exceptions.HandlingException;
import com.example.demo.model.role.Role;
import com.example.demo.model.role.RoleName;
import com.example.demo.model.user.User;
import com.example.demo.payload.request.SignUpRequest;
import com.example.demo.payload.request.UserLoginRequestModel;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.reponsitory.RoleRepository;
import com.example.demo.reponsitory.UserRepository;
import com.example.demo.security.AuthToken;
import com.example.demo.service.UserService;
import com.example.demo.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Configs.URL.AUTH.AUTHS)
public class AuthenticationController {

    private static final String USER_ROLE_NOT_SET = "User role not set";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    Utils utils;

    @GetMapping()
    public String hello() {
        return "Hello World from Tomcat";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> register(@Valid @RequestBody UserLoginRequestModel loginUser) throws AuthenticationException {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.getUsername(),
                            loginUser.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(authentication);
            return ResponseEntity.ok(new AuthToken(token));
        } catch (Exception e) {
            throw new HandlingException(HttpStatus.BAD_REQUEST, "Username or Password is incorrect");
        }
    }

    /**
     * Resgister user
     *
     * @param signUpRequest
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        //Create list to get roles from db
        List<Role> listRoles = new ArrayList();
        List<Role> listRolesIgnoreAdminRole = new ArrayList();
        listRoles = roleRepository.findAll();

        for (int i = 0; i < listRoles.size(); i++) {
            if (listRoles.get(i).getName() != RoleName.ADMIN) {
                listRolesIgnoreAdminRole.add(listRoles.get(i));
            }
        }

        Boolean usernameMatches = Configs.isValidTextRegrex(signUpRequest.getUsername(), Constants.REGREX.USERNAME);
        if (!usernameMatches) {
            throw new AppException("Username is wrong");
        }

        Boolean passwordMatches = Configs.isValidTextRegrex(signUpRequest.getPassword(), Constants.REGREX.PASSWORD);
        if (!passwordMatches) {
            throw new AppException("Password is wrong");
        }

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new HandlingException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new HandlingException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }

        String firstName = signUpRequest.getFirstName().toLowerCase();

        String lastName = signUpRequest.getLastName().toLowerCase();

        String username = signUpRequest.getUsername().toLowerCase();

        String email = signUpRequest.getEmail().toLowerCase();

        String password = passwordEncoder.encode(signUpRequest.getPassword());

        User user = new User(firstName, lastName, username, email, password);

        Set<Role> roles = new HashSet<>();

        if (userRepository.count() == 0) {
            for (int i = 0; i < listRoles.size(); i++) {
                roles.add(listRoles.get(i));
            }
        } else {
            for (int i = 0; i < listRolesIgnoreAdminRole.size(); i++) {
                roles.add(listRolesIgnoreAdminRole.get(i));
            }
        }

        roles.add(
                roleRepository.findByName(RoleName.USER).orElseThrow(() -> new HandlingException(HttpStatus.NOT_FOUND,
                        EnumConstants.USER_ROLE.getEnumConstants() + ErrorMessages.NOT_FOUND.getErrorMessage())));

        user.setRoles(roles);

        String publicUserId = utils.generateUserId(Configs.USER_ID_LENGTH);

        user.setUserIdHash(publicUserId);

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path(Configs.URL.USER.USERS + "/{userId}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(Boolean.TRUE, "User registered successfully"));
    }
}