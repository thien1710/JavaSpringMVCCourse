package com.example.demo.service.impl;

import com.example.demo.io.repositories.UserRepository;
import com.example.demo.config.Configs;
import com.example.demo.io.entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.shared.Utils;
import com.example.demo.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {

        if (userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record already exists");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String publicUserId = utils.generateUserId(Configs.USER_ID_LENGTH);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setUserID(publicUserId);
        userEntity.setEmailVerificationStatus(false);

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException(email);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserByUserID(String userID) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserID(userID);

        if (userEntity == null)
            throw new UsernameNotFoundException(userID);

        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId,UserDto user) {
        UserDto returnValue = new UserDto();
//        UserEntity updateUserDetails;
        UserEntity userEntity = userRepository.findByUserID(userId);

        if (userEntity == null) {
            throw new UsernameNotFoundException("temp");
//            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        userEntity.setFirstname(user.getFirstname());
        userEntity.setLastname(user.getLastname());

//        try {
//            if (user.getFirstName() != null) userEntity.setFirstName(user.getFirstName());
//            if (user.getLastName() != null) userEntity.setLastName(user.getLastName());
//            if (user.getPassword() != null) {
//                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//                String encryptPassword = bCryptPasswordEncoder.encode(user.getPassword());
//                userEntity.setEncryptedPassword(encryptPassword);
//            }
//
//            updateUserDetails = userRepository.save(userEntity);
//        } catch (Exception exception) {
//            System.out.println(exception.getMessage());
//            throw new UserServiceException(ErrorMessages.COULD_NOT_UPDATE_RECORD.getErrorMessage());
//        }

        UserEntity updatedUserDetails = userRepository.save(userEntity);

        BeanUtils.copyProperties(updatedUserDetails, returnValue);
        return returnValue;
    }

    @Override
    public void deleteUser(String id) {
        UserEntity userEntity = userRepository.findByUserID(id);

        if (userEntity == null) {
            throw new UsernameNotFoundException("temp");
//            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        try {
            userRepository.delete(userEntity);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
//            throw new UserServiceException(ErrorMessages.COUNT_NOT_DELETE_RECORD.getErrorMessage());
            throw new UsernameNotFoundException("temp");
        }
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();
        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();

        for (UserEntity userEntity : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }
        return returnValue;
    }

    @Override
    public List<UserDto> getUsersByFirstname(String fn) {
        List<UserDto> returnValue = new ArrayList<>();
        List<UserEntity> users = userRepository.findAll(fn);

        for (UserEntity userEntity : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException(email);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}