package com.example.demo.controller;

import javax.validation.Valid;

import com.example.demo.config.Configs;
import com.example.demo.config.Constants;
import com.example.demo.exceptions.AppException;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.payload.request.*;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.payload.response.ForgotPasswordResponse;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Configs.URL.USER.USERS)
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody UserAddResquest userAddResquest) {
        Boolean usernameMatches = Configs.isValidTextRegrex(userAddResquest.getUsername(), Constants.REGREX.USERNAME);
        if (!usernameMatches) {
            throw new AppException("Username is wrong");
        }

        Boolean passwordMatches = Configs.isValidTextRegrex(userAddResquest.getPassword(), Constants.REGREX.PASSWORD);
        if (!passwordMatches) {
            throw new AppException("Password is wrong");
        }
        ApiResponse newUser = userService.addUser(userAddResquest);

        return new ResponseEntity<ApiResponse>(newUser, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public User getUser(@PathVariable(value = "id") Long id) {

        User user = userService.getUserById(id);

        return user;
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User newUser,
                                           @PathVariable(value = "username") String username) {
        User updatedUSer = userService.updateUser(newUser, username);

        return new ResponseEntity<User>(updatedUSer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable(value = "username") long id) {
        ApiResponse apiResponse = userService.deleteUser(id);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        User user = userService.getCurrentUser(authentication.getName());

        return new ResponseEntity<User>(user, HttpStatus.OK);

    }

    @PutMapping("/{username}/giveAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> giveAdmin(@PathVariable(name = "username") Long id) {
        ApiResponse apiResponse = userService.giveAdmin(id);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{username}/takeAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> takeAdmin(@PathVariable(name = "username") long id) {
        ApiResponse apiResponse = userService.removeAdmin(id);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @PostMapping(path = Configs.URL.USER.PASSWORD_FORGOT_REQUEST_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
    consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ForgotPasswordResponse requestForget(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
        ForgotPasswordResponse returnValue = new ForgotPasswordResponse();

        String tokenForgotPassword = userService.requestPasswordForgot(passwordResetRequestModel.getEmail());

        if (tokenForgotPassword != null){
            returnValue.setDescription(RequestOperationName.REQUEST_PASSWORD_RESET_SUCCESSFUL.name());
            returnValue.setResponseStatus(RequestOperationStatus.SUCCESS.name());
            returnValue.setToken(tokenForgotPassword);
        } else {
            returnValue.setDescription(RequestOperationName.REQUEST_PASSWORD_RESET_FAIL.name());
            returnValue.setResponseStatus(RequestOperationStatus.ERROR.name());
        }


        return  returnValue;
    }

    @PutMapping(path = Configs.URL.USER.PASSWORD_RESET_REQUEST_URL + "/test",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public String requestReset(@RequestParam String token,
                                               @RequestParam String password) {
        Boolean passwordMatches = Configs.isValidTextRegrex(password, Constants.REGREX.PASSWORD);
        if (!passwordMatches) {
            throw new AppException("Password is wrong");
        }
        return userService.requestPasswordReset(token, password);
    }

    @GetMapping(path = "/search",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<User> getUserByFirstname(@RequestParam(value = "keyword") String keyword) {
        List<User> returnValue = new ArrayList<>();

//        List<UserDto> users = userService.getUsersFilter(keyword);
//        for (UserDto userDto : users) {
//            UserRest userModel = new UserRest();
//            BeanUtils.copyProperties(userDto, userModel);
//            returnValue.add(userModel);
//        }
        returnValue = userService.getUsersFilter(keyword);

        return returnValue;
    }

    @GetMapping(path = "/search1",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Project> getUserByFirstnameProject(@RequestBody TestSearch testSearch) {
        List<Project> returnValue = new ArrayList<>();

//        List<UserDto> users = userService.getUsersFilter(keyword);
//        for (UserDto userDto : users) {
//            UserRest userModel = new UserRest();
//            BeanUtils.copyProperties(userDto, userModel);
//            returnValue.add(userModel);
//        }
        returnValue = userService.getUsersFilterProject(testSearch.getInput1(), testSearch.getInput2());

        return returnValue;
    }


}
