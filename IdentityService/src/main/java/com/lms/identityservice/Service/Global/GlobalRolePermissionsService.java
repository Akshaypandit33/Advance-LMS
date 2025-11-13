package com.lms.identityservice.Service.Global;

import com.lms.identityservice.Model.Global.GlobalRolePermissions;


import java.util.List;
import java.util.UUID;

public interface  GlobalRolePermissionsService {

    GlobalRolePermissions createRolePermission(UUID roleId, UUID permissionId);
    GlobalRolePermissions getRolePermissionById(UUID id);
    List<GlobalRolePermissions> getAllRolePermissions();
    List<GlobalRolePermissions> getRolePermissionsByRoleId(UUID roleId);
    List<GlobalRolePermissions> getRolePermissionsByPermissionId(UUID permissionId);
    void deleteRolePermission(UUID id);
    boolean existsByRoleAndPermission(UUID roleId, UUID permissionId);

}
