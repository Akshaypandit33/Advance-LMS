package com.lms.usermanagementservice.BusinessLogic;

import com.LMS.Constants.AccountStatus;
import com.LMS.DTOs.UserService.UserRequestDTO;
import com.LMS.DTOs.UserService.UserResponseDTO;
import com.LMS.Exceptions.UserService.EmailAlreadyExistsException;


import com.LMS.Exceptions.UserService.UserNotFoundException;
import com.lms.usermanagementservice.Mapper.UserEntityToResponseDTO;
import com.lms.usermanagementservice.Mapper.UserRequestDTOToEntityMapper;
import com.lms.usermanagementservice.Model.Users;
import com.lms.usermanagementservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserBusinessLogic {

    private final UserRepository userRepository;
    private final UserRequestDTOToEntityMapper userRequestDTOToEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Users createUser(UserRequestDTO userRequestDTO) {
        String email = Optional.ofNullable(userRequestDTO.email())
                .map(String::toLowerCase)
                .orElseThrow(() -> new IllegalArgumentException("Email is required"));

        boolean exists = userRepository.existsByEmail(email);
        if (exists) {
            throw new EmailAlreadyExistsException("Email Already Exists");
        }
        String encodedPassword = passwordEncoder.encode(userRequestDTO.password());

        return userRepository.save(userRequestDTOToEntityMapper.apply(userRequestDTO, encodedPassword));

    }

    @Transactional
    public Users updateUser(UserRequestDTO userRequestDTO, String email) {
        Users user = userRepository.findByEmail(email.toLowerCase()).orElseThrow(
                () -> new UserNotFoundException("User not found" + email)
        );

        return null;
    }

    public Users getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found" + id)
        );
    }
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase()).orElseThrow(
                () -> new UserNotFoundException("User not found " + email)
        );
    }
    public Map<String,String> deleteUser(UUID id)
    {
        Users user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found" + id)
        );
        userRepository.deleteById(id);
        Map<String,String> map = new HashMap<>();
        map.put("status","success");
        map.put("message","User deleted successfully with id: " + id);
        return map;
    }

    public Users changeAccountStatus(UUID id, String status) {
        Users user = getUserById(id);
        user.setAccountStatus(AccountStatus.valueOf(status.toUpperCase()));

        return userRepository.save(user);


    }

    public Page<Users> searchUsers(Pageable pageable, String query) {
        return  userRepository.searchUsers(query,pageable);
    }
    public Page<Users> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Users findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );
    }



}
