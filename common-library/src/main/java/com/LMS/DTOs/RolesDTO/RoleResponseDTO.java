package com.LMS.DTOs.RolesDTO;

import java.util.UUID;

public record RoleResponseDTO(
        UUID roleId,
        String roleName,
        String descriptions
) {
}
