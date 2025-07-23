package com.lms.usermanagementservice.BusinessLogic;

import com.LMS.Exceptions.RoleService.RoleNotFoundException;
import com.LMS.Exceptions.UnAuthorizedOperationException;
import com.LMS.Exceptions.UserService.UserNotFoundException;
import com.lms.usermanagementservice.Model.Roles;
import com.lms.usermanagementservice.Model.UserRole;
import com.lms.usermanagementservice.Model.Users;
import com.lms.usermanagementservice.Repository.RolesRepository;
import com.lms.usermanagementservice.Repository.UserRepository;
import com.lms.usermanagementservice.Repository.UserRolesRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class UserRoleBusinessLogic {

    private final UserRolesRepository userRolesRepository;
    private final RolesRepository rolesRepository;
    private final UserRepository userRepository;


    @Transactional
    public UserRole assignUserRole(UUID roleId, UUID userId) {
        Roles role = rolesRepository.findById(roleId).orElseThrow(
                () -> new RoleNotFoundException("Role with id " + roleId + " not found")
        );

        Users user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with id " + userId+ " not found")
        );
       return userRolesRepository
                .findUserRoleByRole_IdAndUser_Id(roleId, userId)
                .orElseGet(
                () -> userRolesRepository.save(
                        UserRole.builder()
                                .user(user)
                                .role(role)
                                .build()
                )
        );
    }

    @Transactional
    public List<UserRole> assignMultipleRoleToUser(UUID userId, List<UUID> roleIdList)
    {
        List<UserRole> userRoles = new ArrayList<>();
        for(UUID roleId : roleIdList)
        {
            userRoles.add(assignUserRole(roleId, userId));
        }
        return userRoles;
    }
}
