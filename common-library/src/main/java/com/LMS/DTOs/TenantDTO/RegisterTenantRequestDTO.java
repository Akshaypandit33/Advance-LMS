package com.LMS.DTOs.TenantDTO;

public record RegisterTenantRequestDTO(

        String name,
        String collegeCode,
        String email,
        String phoneNumber,
        String schemaName,
        String logoUrl,
        String theme,
        String status
) {
}
