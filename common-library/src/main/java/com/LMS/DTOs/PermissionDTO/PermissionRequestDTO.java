package com.LMS.DTOs.PermissionDTO;


import java.util.UUID;

public record PermissionRequestDTO(
    UUID actionId,
    UUID resourceId
) {
}
