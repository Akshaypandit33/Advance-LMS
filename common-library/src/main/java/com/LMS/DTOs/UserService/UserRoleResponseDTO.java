package com.LMS.DTOs.UserService;

import java.util.UUID;

public record UserRoleResponseDTO(
        UUID userRoleId,
        UUID userId,
        String fullName,
        UUID roleId,
        String roleName,
        UUID tenantId
) {
}
