package com.LMS.DTOs.RolePermissionDTO;

import java.util.List;
import java.util.UUID;

public record RolePermissionBulkRequestDTO(
        UUID roleId,
        List<UUID> permissionIds
){};
