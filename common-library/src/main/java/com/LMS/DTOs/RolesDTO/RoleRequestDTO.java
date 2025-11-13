package com.LMS.DTOs.RolesDTO;

public record RoleRequestDTO(
        String roleName,
        String descriptions,
        boolean isSystemRole,
        boolean isTemplate
) {
}
