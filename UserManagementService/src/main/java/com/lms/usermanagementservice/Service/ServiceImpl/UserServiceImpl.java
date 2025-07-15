package com.lms.usermanagementservice.Service.ServiceImpl;

import com.LMS.DTOs.UserService.UserRequestDTO;
import com.LMS.DTOs.UserService.UserResponseDTO;
import com.lms.usermanagementservice.Model.Users;
import com.lms.usermanagementservice.Repository.UserRolesRepository;
import com.lms.usermanagementservice.Service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRolesRepository userRolesRepository;

    @Transactional
    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        Users user = Users.builder()
                .firstName(userRequestDTO.firstName())
                .lastName(userRequestDTO.lastName())
                .password(passwordEncoder.encode(userRequestDTO.password()))
                .email(userRequestDTO.email())
                .password(passwordEncoder.encode(userRequestDTO.password()))
                .gender(userRequestDTO.gender())
                .phoneNumber(userRequestDTO.phoneNumber())
                .accountStatus(userRequestDTO.accountStatus())
                .build();


        return null;
    }

}
