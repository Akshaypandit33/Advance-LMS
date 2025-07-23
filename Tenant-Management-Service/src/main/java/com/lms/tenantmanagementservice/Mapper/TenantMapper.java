package com.lms.tenantmanagementservice.Mapper;

import com.LMS.Constants.TenantStatus;
import com.LMS.DTOs.TenantDTO.RegisterTenantRequestDTO;
import com.LMS.DTOs.TenantDTO.TenantResponseDTO;
import com.lms.tenantmanagementservice.Model.Tenants;
import org.springframework.stereotype.Component;


public class TenantMapper {

    public static Tenants toEntity(RegisterTenantRequestDTO tenantDTO) {
        return Tenants.builder()
                .name(tenantDTO.name())
                .email(tenantDTO.email())
                .status(TenantStatus.valueOf(tenantDTO.status().toUpperCase()))
                .theme(tenantDTO.theme())
                .collegeCode(tenantDTO.collegeCode())
                .logoUrl(tenantDTO.logoUrl())
                .phoneNumber(tenantDTO.phoneNumber())
                .schemaName(tenantDTO.schemaName())
                .build();
    }

    public static TenantResponseDTO toDTO(Tenants tenants) {
        return new TenantResponseDTO(
                tenants.getId(),
                tenants.getName(),
                tenants.getCollegeCode(),
                tenants.getEmail(),
                tenants.getPhoneNumber(),
                tenants.getSchemaName(),
                tenants.getStatus().name(),
                tenants.getLogoUrl(),
                tenants.getTheme(),
                tenants.getAddedAt()
        );
    }
}
