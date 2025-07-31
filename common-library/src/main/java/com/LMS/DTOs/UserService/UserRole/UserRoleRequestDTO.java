package com.LMS.DTOs.UserService.UserRole;

import java.util.UUID;

public record UserRoleRequestDTO(
        UUID userId,
        String roleName
) {
}
