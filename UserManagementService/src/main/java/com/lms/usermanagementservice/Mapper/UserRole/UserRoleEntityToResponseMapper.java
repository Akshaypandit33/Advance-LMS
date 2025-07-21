package com.lms.usermanagementservice.Mapper.UserRole;

import com.LMS.DTOs.UserService.UserRoleResponseDTO;
import com.lms.usermanagementservice.Model.UserRole;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserRoleEntityToResponseMapper implements Function<UserRole, UserRoleResponseDTO> {
    @Override
    public UserRoleResponseDTO apply(UserRole userRole) {
        return new UserRoleResponseDTO(
                userRole.getId(),
                userRole.getUser().getId(),
                userRole.getUser().getFirstName() + " "+ userRole.getUser().getLastName(),
                userRole.getRole().getId(),
                userRole.getRole().getRoleName(),
                userRole.getTenantId()

        );
    }
}
