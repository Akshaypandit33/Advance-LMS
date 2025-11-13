package com.LMS.DTOs.RolePermissionDTO;

import java.util.UUID;

public record RolePermissionRequestDTO(
        UUID roleId,
        UUID permissionId
) {
}
