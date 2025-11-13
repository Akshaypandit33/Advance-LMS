package com.lms.tenantmanagementservice.Service.Impl;

import com.LMS.DTOs.TenantDTO.RegisterTenantRequestDTO;
import com.LMS.DTOs.TenantDTO.TenantResponseDTO;
import com.LMS.Exceptions.TenantService.TenantNotFoundExceptions;
import com.LMS.Kafka.TenantAddedEvent;
import com.lms.tenantmanagementservice.Kafka.TenantEventProducer;
import com.lms.tenantmanagementservice.Mapper.TenantMapper;
import com.lms.tenantmanagementservice.Model.Tenants;
import com.lms.tenantmanagementservice.Repository.TenantRepository;
import com.lms.tenantmanagementservice.Service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final TenantEventProducer tenantEventProducer;

    @Override
    public TenantResponseDTO addTenant(RegisterTenantRequestDTO registerTenantRequestDTO) {
        String normalizedCode = registerTenantRequestDTO.collegeCode().trim().toUpperCase();

        Tenants savedTenant = tenantRepository.findByCollegeCode(normalizedCode)
                .orElseGet(() -> {
                    Tenants newTenant = TenantMapper.toEntity(registerTenantRequestDTO);
                    newTenant.setCollegeCode(normalizedCode);
                    return tenantRepository.save(newTenant);
                });

        TenantAddedEvent tenantAddedEvent = new TenantAddedEvent(
                savedTenant.getId().toString(),
                savedTenant.getName(),
                savedTenant.getCollegeCode(),
                savedTenant.getSchemaName()
        );
        tenantEventProducer.tenantAddedEvent(tenantAddedEvent);
        return TenantMapper.toDTO(savedTenant);
    }



    @Transactional(readOnly = true)
    @Override
    public Page<TenantResponseDTO> getAllTenants(Pageable pageable) {

        return tenantRepository.findAll(pageable).map(TenantMapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public TenantResponseDTO getTenantById(UUID id) {
        return TenantMapper.toDTO(tenantRepository.findById(id).orElseThrow(
                () -> new TenantNotFoundExceptions("Tenant with id " + id + " not found")
        ));
    }

    @Override
    public void deleteTenant(UUID tenantId) {
        if (!tenantRepository.existsById(tenantId)) {
            throw new TenantNotFoundExceptions("Tenant with id " + tenantId + " not found");
        }
         tenantRepository.deleteTenantsById(tenantId);
    }
}
