package com.LMS.DTOs.UserService.UserRole;

import java.time.ZonedDateTime;
import java.util.UUID;

public record UserRoleInfoDTO(
        UUID userRoleId,
        UUID roleId,
        String roleName,
        ZonedDateTime assignedAt
) {}
