package com.lms.tenantmanagementservice.Controller;

import com.LMS.DTOs.TenantDTO.RegisterTenantRequestDTO;
import com.LMS.DTOs.TenantDTO.TenantResponseDTO;
import com.LMS.Kafka.TenantAddedEvent;
import com.lms.tenantmanagementservice.Kafka.TenantEventProducer;
import com.lms.tenantmanagementservice.Service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;
    private final TenantEventProducer tenantEventProducer;

    /**
     * Register a new tenant. If a tenant with the given college code already exists, returns the existing one.
     */
    @PostMapping
    public ResponseEntity<TenantResponseDTO> registerTenant(
            @Valid @RequestBody RegisterTenantRequestDTO requestDTO) {
        TenantResponseDTO createdTenant = tenantService.addTenant(requestDTO);
        return ResponseEntity.ok(createdTenant); // 200 OK with response body
    }

    /**
     * Get a list of all tenants with pagination support.
     */
    @GetMapping
    public ResponseEntity<Page<TenantResponseDTO>> getAllTenants(Pageable pageable) {
        Page<TenantResponseDTO> tenants = tenantService.getAllTenants(pageable);
        return ResponseEntity.ok(tenants);
    }

    /**
     * Get tenant details by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TenantResponseDTO> getTenantById(@PathVariable UUID id) {
        TenantResponseDTO tenant = tenantService.getTenantById(id);
        return ResponseEntity.ok(tenant);
    }

    /**
     * Delete a tenant by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable UUID id) {
        tenantService.deleteTenant(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/testKafka")
    public void testKafka() {
        tenantEventProducer.tenantAddedEvent(new TenantAddedEvent(
                UUID.randomUUID().toString(),
                "NIET",
                "NIET001",
                "NIET_SCHEMA"
        ));
        System.out.println("Event sent to kafka topic");
    }
}
