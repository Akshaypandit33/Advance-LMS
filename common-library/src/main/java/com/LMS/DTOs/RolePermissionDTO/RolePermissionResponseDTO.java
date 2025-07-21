package com.LMS.DTOs.RolePermissionDTO;

import com.LMS.DTOs.PermissionDTO.PermissionResponseDTO;
import com.LMS.DTOs.RolesDTO.RoleResponseDTO;

import java.util.UUID;

public record RolePermissionResponseDTO(
        UUID rolePermissionId,
        RoleResponseDTO roles,
        PermissionResponseDTO permissions
) {
}
