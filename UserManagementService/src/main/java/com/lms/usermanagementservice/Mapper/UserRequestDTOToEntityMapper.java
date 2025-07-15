package com.lms.usermanagementservice.Mapper;

import com.LMS.DTOs.UserService.UserRequestDTO;
import com.lms.usermanagementservice.Model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserRequestDTOToEntityMapper implements Function<UserRequestDTO, Users> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Users apply(UserRequestDTO userRequestDTO) {
        return
                Users.builder()
                        .firstName(userRequestDTO.firstName())
                        .lastName(userRequestDTO.lastName())
                        .password(passwordEncoder.encode(userRequestDTO.password()))
                        .email(userRequestDTO.email())
                        .gender(userRequestDTO.gender())
                        .tenantId(userRequestDTO.tenantId())
                        .phoneNumber(userRequestDTO.phoneNumber())
                        .accountStatus(userRequestDTO.accountStatus())
                        .build();
    }
}
