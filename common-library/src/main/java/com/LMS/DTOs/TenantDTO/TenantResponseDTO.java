package com.LMS.DTOs.TenantDTO;


import java.time.ZonedDateTime;
import java.util.UUID;

public record TenantResponseDTO(
        UUID tenantId,
        String name,
        String collegeCode,
        String email,
        String phoneNumber,
        String schemaName,
        String status,
        String logoUrl,
        ZonedDateTime addedAt
) {
}
