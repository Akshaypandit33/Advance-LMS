package com.LMS.DTOs.UserService.UserRole;

import java.time.ZonedDateTime;
import java.util.UUID;

public record UserRoleResponseDTO(
        UUID userRoleId,
        UUID userId,
        String fullName,
        UUID roleId,
        String roleName,
        ZonedDateTime assignedAt
) {
}
