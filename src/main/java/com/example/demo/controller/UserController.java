package com.example.demo.controller;

import javax.validation.Valid;

import com.example.demo.config.Configs;
import com.example.demo.model.user.User;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.service.UserService;
import com.example.demo.shared.dto.UserDto;
import com.example.demo.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Configs.URL.USER.SIGN_UP)
public class UserController {
    @Autowired
    private UserService userService;

//    @Autowired
//    private PostService postService;
//
//    @Autowired
//    private AlbumService albumService;

//    @GetMapping("/me")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<UserSummary> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
//        UserSummary userSummary = userService.getCurrentUser(currentUser);
//
//        return new ResponseEntity<UserSummary>(userSummary, HttpStatus.OK);
//    }
//
//    @GetMapping("/checkUsernameAvailability")
//    public ResponseEntity<UserIdentityAvailability> checkUsernameAvailability(@RequestParam(value = "username") String username) {
//        UserIdentityAvailability userIdentityAvailability = userService.checkUsernameAvailability(username);
//
//        return new ResponseEntity<UserIdentityAvailability>(userIdentityAvailability, HttpStatus.OK);
//    }
//
//    @GetMapping("/checkEmailAvailability")
//    public ResponseEntity<UserIdentityAvailability> checkEmailAvailability(@RequestParam(value = "email") String email) {
//        UserIdentityAvailability userIdentityAvailability =  userService.checkEmailAvailability(email);
//        return new ResponseEntity<UserIdentityAvailability>(userIdentityAvailability, HttpStatus.OK);
//    }
//
//    @GetMapping("/{username}/profile")
//    public ResponseEntity<UserProfile> getUSerProfile(@PathVariable(value = "username") String username) {
//        UserProfile userProfile = userService.getUserProfile(username);
//
//        return new ResponseEntity<UserProfile>(userProfile, HttpStatus.OK);
//    }
//
//    @GetMapping("/{username}/posts")
//    public ResponseEntity<PagedResponse<Post>> getPostsCreatedBy(@PathVariable(value = "username") String username,
//                                                                 @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
//                                                                 @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
//        PagedResponse<Post> response = postService.getPostsByCreatedBy(username, page, size);
//
//        return new ResponseEntity<PagedResponse<Post>>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/{username}/albums")
//    public ResponseEntity<PagedResponse<Album>> getUserAlbums(@PathVariable(name = "username") String username,
//                                                              @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
//                                                              @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
//
//        PagedResponse<Album> response = albumService.getUserAlbums(username, page, size);
//
//        return new ResponseEntity<PagedResponse<Album>>(response, HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User newUser = userService.addUser(user);

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public UserRest getUser(@PathVariable Long id) {
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserById(id);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }
    @PutMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User newUser,
                                           @PathVariable(value = "username") String username) {
        User updatedUSer = userService.updateUser(newUser, username);

        return new ResponseEntity<User>(updatedUSer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable(value = "username") String username) {
        ApiResponse apiResponse = userService.deleteUser(username);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }
//
//    @PutMapping("/{username}/giveAdmin")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<ApiResponse> giveAdmin(@PathVariable(name = "username") String username) {
//        ApiResponse apiResponse = userService.giveAdmin(username);
//
//        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
//    }
//
//    @PutMapping("/{username}/takeAdmin")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<ApiResponse> takeAdmin(@PathVariable(name = "username") String username) {
//        ApiResponse apiResponse = userService.removeAdmin(username);
//
//        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
//    }
//
//    @PutMapping("/setOrUpdateInfo")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<UserProfile> setAddress(@CurrentUser UserPrincipal currentUser,
//                                                  @Valid @RequestBody InfoRequest infoRequest) {
//        UserProfile userProfile = userService.setOrUpdateInfo(currentUser, infoRequest);
//
//        return new ResponseEntity<UserProfile>(userProfile, HttpStatus.OK);
//    }

}
