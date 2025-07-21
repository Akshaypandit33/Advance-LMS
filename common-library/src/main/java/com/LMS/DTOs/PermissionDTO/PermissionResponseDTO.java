package com.LMS.DTOs.PermissionDTO;

import com.LMS.DTOs.ActionsDTO.ActionResponseDTO;
import com.LMS.DTOs.ResourceDTO.ResourceResponseDTO;

import java.util.UUID;

public record PermissionResponseDTO(
        UUID permissionId,
        ActionResponseDTO actions,
        ResourceResponseDTO resources
) {
}
