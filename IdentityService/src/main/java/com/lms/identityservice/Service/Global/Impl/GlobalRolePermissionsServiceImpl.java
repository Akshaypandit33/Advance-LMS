package com.lms.identityservice.Service.Global.Impl;

import com.LMS.Exceptions.PermissionService.PermissionNotFoundException;
import com.LMS.Exceptions.ResourceService.ResourceAlreadyExistsException;
import com.LMS.Exceptions.RoleService.RoleNotFoundException;
import com.lms.identityservice.Model.Global.GlobalPermissions;
import com.lms.identityservice.Model.Global.GlobalRolePermissions;
import com.lms.identityservice.Model.Global.GlobalRoles;

import com.lms.identityservice.Repository.Global.GlobalPermissionRepository;
import com.lms.identityservice.Repository.Global.GlobalRolePermissionRepository;
import com.lms.identityservice.Repository.Global.GlobalRoleRepository;
import com.lms.identityservice.Service.Global.GlobalRolePermissionsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GlobalRolePermissionsServiceImpl implements GlobalRolePermissionsService {

    private final GlobalRolePermissionRepository globalRolePermissionsRepository;
    private final GlobalRoleRepository globalRoleRepository;
    private final GlobalPermissionRepository globalPermissionRepository;

    @Override
    @Transactional
    public GlobalRolePermissions createRolePermission(UUID roleId, UUID permissionId) {
        var role = globalRoleRepository.findById(roleId).orElseThrow(
                () -> new RoleNotFoundException("Role with id " + roleId + " not found")
        );

        var permission = globalPermissionRepository.findById(permissionId).orElseThrow(
                () -> new PermissionNotFoundException("Permission with id " + permissionId + " not found")
        );

        if (existsByRoleAndPermission(role.getId(), permission.getId())) {
            throw new ResourceAlreadyExistsException("This role-permission association already exists");
        }

        var rolePermission = GlobalRolePermissions.builder()
                .roles(role)
                .permissions(permission)
                .build();

        return globalRolePermissionsRepository.save(rolePermission);
    }

    @Override
    public GlobalRolePermissions getRolePermissionById(UUID id) {
        return globalRolePermissionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role-Permission association not found with id: " + id));
    }

    @Override
    public List<GlobalRolePermissions> getAllRolePermissions() {
        return globalRolePermissionsRepository.findAll();
    }

    @Override
    public List<GlobalRolePermissions> getRolePermissionsByRoleId(UUID roleId) {
        return globalRolePermissionsRepository.findByRolesId(roleId);
    }

    @Override
    public List<GlobalRolePermissions> getRolePermissionsByPermissionId(UUID permissionId) {
        return globalRolePermissionsRepository.findByPermissionsId(permissionId);
    }

    @Override
    @Transactional
    public void deleteRolePermission(UUID id) {
        if (!globalRolePermissionsRepository.existsById(id)) {
            throw new RuntimeException("Role-Permission association not found with id: " + id);
        }
        globalRolePermissionsRepository.deleteById(id);
    }

    @Override
    public boolean existsByRoleAndPermission(UUID roleId, UUID permissionId) {
        return globalRolePermissionsRepository.existsByRolesIdAndPermissionsId(roleId, permissionId);
    }
}