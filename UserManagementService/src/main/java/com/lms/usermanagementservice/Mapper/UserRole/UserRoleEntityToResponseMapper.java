package com.lms.usermanagementservice.Mapper.UserRole;

import com.LMS.DTOs.UserService.UserRole.*;
import com.lms.usermanagementservice.Model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleEntityToResponseMapper  {

    public  UserRoleResponseDTOList ListToResponseDTO(List<UserRole> userRole) {
        List<UserRoleInfoDTO> roles = userRole.stream()
                .map(ur -> new UserRoleInfoDTO(
                        ur.getId(),
                        ur.getRole().getId(),
                        ur.getRole().getRoleName(),
                        ur.getAssignedAt()
                ))
                .toList();
        UserRole uRole =  userRole.getFirst();
        return new UserRoleResponseDTOList(
                uRole.getUser().getId(),
                uRole.getUser().getFirstName()+" "+uRole.getUser().getLastName(),
                roles
        );
    }
    public UserRoleResponseDTO ToResponseDTO(UserRole userRole) {
        return new UserRoleResponseDTO(
                userRole.getId(),
                userRole.getUser().getId(),
                userRole.getUser().getFirstName()+" "+userRole.getUser().getLastName(),
                userRole.getRole().getId(),
                userRole.getRole().getRoleName(),
                userRole.getAssignedAt()
                );
    }

    public FetchByRoleDTO toFetchByRoleDTO(Page<UserRole> userRolePage) {
        List<UserInfoDTO> userInfoDTOList = userRolePage.stream()
                .map(ur -> new UserInfoDTO(
                        ur.getUser().getId(),
                        ur.getUser().getFirstName() + " " + ur.getUser().getLastName(),
                        ur.getUser().getEmail()
                ))
                .toList();

        if (userRolePage.isEmpty()) {
            // You could throw a custom exception or return a DTO with nulls or empty fields
            return new FetchByRoleDTO(null, null, null, userInfoDTOList);
        }

        UserRole firstUserRole = userRolePage.getContent().get(0);

        return new FetchByRoleDTO(
                firstUserRole.getRole().getId(),
                firstUserRole.getRole().getRoleName(),
                firstUserRole.getRole().getRoleDescription(),
                userInfoDTOList
        );
    }

}
