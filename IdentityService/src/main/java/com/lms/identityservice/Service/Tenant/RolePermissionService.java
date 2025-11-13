package com.lms.identityservice.Service.Tenant;


import com.lms.identityservice.Model.Tenant.RolePermission;

import java.util.List;
import java.util.UUID;

public interface RolePermissionService {
    RolePermission createRolePermission(UUID roleId, UUID permissionId);
    List<RolePermission> createRolePermissions(UUID roleId, List<UUID> permissionIds);
    RolePermission getRolePermissionById(UUID id);
    List<RolePermission> getAllRolePermissions();
    List<RolePermission> getRolePermissionsByRoleId(UUID roleId);
    List<RolePermission> getRolePermissionsByPermissionId(UUID permissionId);
    void deleteRolePermission(UUID id);
    boolean existsByRoleAndPermission(UUID roleId, UUID permissionId);
     boolean hasAllPermissions(UUID roleId, List<UUID> permissionIds);
}
