package com.lms.tenantmanagementservice.Service;

import com.LMS.DTOs.TenantDTO.RegisterTenantRequestDTO;
import com.LMS.DTOs.TenantDTO.TenantResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TenantService {

    TenantResponseDTO addTenant(RegisterTenantRequestDTO registerTenantRequestDTO);
    Page<TenantResponseDTO> getAllTenants(Pageable pageable);

    TenantResponseDTO getTenantById(UUID id);
    void deleteTenant(UUID tenantId);
}
