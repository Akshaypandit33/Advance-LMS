package com.lms.identityservice.Service.Global;


import com.LMS.DTOs.PermissionDTO.PermissionRequestDTO;
import com.lms.identityservice.Model.Global.GlobalPermissions;

import java.util.List;
import java.util.UUID;

public interface GlobalPermissionService {
    GlobalPermissions createPermission(PermissionRequestDTO permission);
    GlobalPermissions getPermissionById(UUID id);
    GlobalPermissions getPermissionByName(String name);
    List<GlobalPermissions> getAllPermissions();
    List<GlobalPermissions> getPermissionsByAction(UUID actionId);
    List<GlobalPermissions> getPermissionsByResource(UUID resourceId);

    void deletePermission(UUID id);
    boolean permissionExists(String name);
    boolean permissionExistsForActionAndResource(UUID actionId, UUID resourceId);
}
