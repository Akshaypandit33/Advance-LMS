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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Component
@RequiredArgsConstructor
public class UserRoleBusinessLogic {

    private final UserRolesRepository userRolesRepository;
    private final RolesRepository rolesRepository;
    private final UserRepository userRepository;


    @Transactional
    public UserRole assignUserRole(String roleName, UUID userId) {
        Roles role = rolesRepository.findByRoleName(roleName.toUpperCase()).orElseThrow(
                () -> new RoleNotFoundException("Role with name " + roleName.toUpperCase() + " not found")
        );

        Users user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with id " + userId+ " not found")
        );
       return userRolesRepository
                .findUserRoleByRole_RoleNameAndUser_Id(roleName.toUpperCase(), userId)
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
    public List<UserRole> assignMultipleRoleToUser(UUID userId, List<String> roleIdList)
    {
        List<UserRole> userRoles = new ArrayList<>();
        for(String roleName : roleIdList)
        {
            userRoles.add(assignUserRole(roleName, userId));
        }
        return userRoles;
    }

    @Transactional
    public Map<String, String> revokeUserRole(UUID userId, String roleName) {
        Roles role = rolesRepository.findByRoleName(roleName.trim().toUpperCase())
                .orElseThrow(() -> new RoleNotFoundException("Role with name " + roleName + " not found"));

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        Optional<UserRole> userRole = userRolesRepository.findUserRoleByRole_IdAndUser_Id(role.getId(), userId);

        Map<String, String> response = new HashMap<>();
        if (userRole.isPresent()) {
            userRolesRepository.deleteById(userRole.get().getId());
            response.put("status", "success");
            response.put("message", "User role '" + roleName.toUpperCase() + "' has been revoked from user " + userId);
        } else {
            response.put("status", "failure");
            response.put("message", "User " + userId + " does not have role '" + roleName.toUpperCase() + "'");
        }

        return response;
    }

    public List<UserRole> findUserRolesByUser_Id(UUID userId) {
        return userRolesRepository.findUserRolesByUser_Id(userId);
    }

    public Page<UserRole> findUsersByRole(String roleName, Pageable  pageable) {
        return userRolesRepository.findUserRolesByRole_RoleName(roleName.toUpperCase(), pageable);
    }

    public Page<UserRole> findAllUserRoles(Pageable pageable) {
        return userRolesRepository.findAll(pageable);
    }
}
