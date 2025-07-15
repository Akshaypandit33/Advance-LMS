package com.lms.usermanagementservice.Mapper;

import com.LMS.DTOs.UserService.UserResponseDTO;
import com.lms.usermanagementservice.Model.Roles;
import com.lms.usermanagementservice.Model.Users;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserEntityToResponseDTO implements Function<Users, UserResponseDTO> {

    @Override
    public UserResponseDTO apply(Users user) {
        return new UserResponseDTO(
                user.getId(),
                user.getTenantId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getGender(),
                user.getAccountStatus(),
                user.getRoles().stream().map(Roles::getRoleName).toList()
        );
    }
}
