package com.LMS.DTOs.PermissionDTO;

import com.LMS.DTOs.ActionsDTO.ActionRequestDTO;
import com.LMS.DTOs.ResourceDTO.ResourceRequestDTO;

public record PermissionRequestDTO(
    Long actionId,
    Long resourceId
) {
}
