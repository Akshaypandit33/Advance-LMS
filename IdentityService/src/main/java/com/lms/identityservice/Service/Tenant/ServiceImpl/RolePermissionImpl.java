package com.lms.identityservice.Service.Tenant.ServiceImpl;

import com.LMS.Exceptions.RolePermissionService.RolePermissionAlreadyExistsExceptions;
import com.LMS.Exceptions.RolePermissionService.RolePermissionNotFoundExceptions;
import com.lms.identityservice.Model.Global.GlobalPermissions;
import com.lms.identityservice.Model.Tenant.RolePermission;
import com.lms.identityservice.Model.Tenant.Roles;
import com.lms.identityservice.Repository.Tenant.RolePermissionRepository;
import com.lms.identityservice.Service.Global.GlobalPermissionService;
import com.lms.identityservice.Service.Tenant.RolePermissionService;
import com.lms.identityservice.Service.Tenant.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RolePermissionImpl implements RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    private final GlobalPermissionService globalPermissionService;
    private final RoleService roleService;

    @Override
    public RolePermission createRolePermission(UUID roleId, UUID permissionId) {
        if (existsByRoleAndPermission(roleId, permissionId)) {
            throw new RolePermissionAlreadyExistsExceptions("Role Permission already exists");
        }

        Roles role = roleService.getRoles(roleId); // Should throw if not found
        GlobalPermissions permission = globalPermissionService.getPermissionById(permissionId); // Should throw if not found

        return rolePermissionRepository.save(RolePermission.builder()
                .role(role)
                .permissions(permission)
                .build());
    }

    @Override
    public List<RolePermission> createRolePermissions(UUID roleId, List<UUID> permissionIds) {
        return permissionIds.stream()
                .filter(permissionId -> !existsByRoleAndPermission(roleId, permissionId))
                .map(permissionId -> createRolePermission(roleId, permissionId))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public RolePermission getRolePermissionById(UUID id) {
        return rolePermissionRepository.findById(id)
                .orElseThrow(() -> new RolePermissionNotFoundExceptions("Role permission not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<RolePermission> getAllRolePermissions() {
        return rolePermissionRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<RolePermission> getRolePermissionsByRoleId(UUID roleId) {
        return rolePermissionRepository.findAllByRole_Id(roleId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RolePermission> getRolePermissionsByPermissionId(UUID permissionId) {
        return rolePermissionRepository.findAllByPermissions_Id(permissionId);
    }

    @Override
    public void deleteRolePermission(UUID id) {
        if (!rolePermissionRepository.existsById(id)) {
            throw new RolePermissionNotFoundExceptions("Role permission not found");
        }
        rolePermissionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByRoleAndPermission(UUID roleId, UUID permissionId) {
        return rolePermissionRepository.existsByRole_IdAndPermissions_Id(roleId, permissionId);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean hasAllPermissions(UUID roleId, List<UUID> permissionIds) {
        long count = rolePermissionRepository.countByRole_IdAndPermissions_IdIn(roleId, permissionIds);
        return count == permissionIds.size();
    }
}