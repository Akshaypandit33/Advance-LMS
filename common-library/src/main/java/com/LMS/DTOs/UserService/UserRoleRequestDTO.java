package com.LMS.DTOs.UserService;

import java.util.UUID;

public record UserRoleRequestDTO(
        UUID userId,
        UUID roleId,
        UUID tenantId
) {
}
