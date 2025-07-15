package com.lms.usermanagementservice.BusinessLogic;

import com.LMS.DTOs.UserService.UserRequestDTO;
import com.LMS.DTOs.UserService.UserResponseDTO;
import com.LMS.Exceptions.UserService.EmailAlreadyExistsException;


import com.lms.usermanagementservice.Mapper.UserEntityToResponseDTO;
import com.lms.usermanagementservice.Mapper.UserRequestDTOToEntityMapper;
import com.lms.usermanagementservice.Model.Users;
import com.lms.usermanagementservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserBusinessLogic {

    private final UserRepository userRepository;
    private final UserRequestDTOToEntityMapper userRequestDTOToEntityMapper;
    private final UserEntityToResponseDTO userEntityToResponseDTO;

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        boolean exists = userRepository.existsByEmail(userRequestDTO.email().toLowerCase());
        if (exists) {
            throw new EmailAlreadyExistsException("Email Already Exists");
        }

        Users user = userRepository.save(userRequestDTOToEntityMapper.apply(userRequestDTO));
        return userEntityToResponseDTO.apply(user);
    }
}
