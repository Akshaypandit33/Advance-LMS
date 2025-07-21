package com.lms.usermanagementservice.BusinessLogic;

import com.LMS.DTOs.UserService.UserRequestDTO;
import com.LMS.DTOs.UserService.UserResponseDTO;
import com.LMS.Exceptions.UserService.EmailAlreadyExistsException;


import com.LMS.Exceptions.UserService.UserNotFoundException;
import com.lms.usermanagementservice.Mapper.UserEntityToResponseDTO;
import com.lms.usermanagementservice.Mapper.UserRequestDTOToEntityMapper;
import com.lms.usermanagementservice.Model.Users;
import com.lms.usermanagementservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<Users> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Users findUserById(UUID id) {

        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );
    }


}
