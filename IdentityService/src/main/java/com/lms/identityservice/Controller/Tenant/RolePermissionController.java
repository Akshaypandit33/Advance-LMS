package com.lms.identityservice.Controller.Tenant;

import com.LMS.Constants.GlobalConstant;
import com.LMS.DTOs.RolePermissionDTO.RolePermissionBulkRequestDTO;
import com.LMS.DTOs.RolePermissionDTO.RolePermissionRequestDTO;
import com.LMS.DTOs.RolePermissionDTO.RolePermissionResponseDTO;
import com.lms.identityservice.Mapper.RolePermissionMapper;
import com.lms.identityservice.Service.Tenant.RolePermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/role-permissions")
@RequiredArgsConstructor
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    @Operation(summary = "Create a role-permission mapping")
    @ApiResponse(responseCode = "201", description = "Role-permission mapping created successfully")
    @PostMapping
    public ResponseEntity<RolePermissionResponseDTO> createRolePermission(@RequestHeader(GlobalConstant.HEADER_TENANT_ID) String tenantId,
            @Valid @RequestBody RolePermissionRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RolePermissionMapper.toResponseDTO(
                        rolePermissionService.createRolePermission(
                                requestDTO.roleId(),
                                requestDTO.permissionId()
                        )
                )
        );
    }

    @Operation(summary = "Create multiple role-permission mappings in bulk")
    @ApiResponse(responseCode = "201", description = "Role-permission mappings created successfully")
    @PostMapping("/bulk")
    public ResponseEntity<List<RolePermissionResponseDTO>> createRolePermissions(
            @RequestHeader(GlobalConstant.HEADER_TENANT_ID) String tenantId,
            @Valid @RequestBody RolePermissionBulkRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                rolePermissionService.createRolePermissions(
                                requestDTO.roleId(),
                                requestDTO.permissionIds()
                        ).stream()
                        .map(RolePermissionMapper::toResponseDTO)
                        .collect(Collectors.toList())
        );
    }

    @Operation(summary = "Get role-permission mapping by ID")
    @ApiResponse(responseCode = "200", description = "Role-permission mapping found")
    @GetMapping("/{id}")
    public ResponseEntity<RolePermissionResponseDTO> getRolePermissionById(@PathVariable UUID id,
                                                                           @RequestHeader(GlobalConstant.HEADER_TENANT_ID) String tenantId) {
        return ResponseEntity.ok(
                RolePermissionMapper.toResponseDTO(
                        rolePermissionService.getRolePermissionById(id)
                )
        );
    }

    @Operation(summary = "Get all role-permission mappings")
    @GetMapping
    public ResponseEntity<List<RolePermissionResponseDTO>> getAllRolePermissions(
            @RequestHeader(GlobalConstant.HEADER_TENANT_ID) String tenantId
    ) {
        return ResponseEntity.ok(
                rolePermissionService.getAllRolePermissions().stream()
                        .map(RolePermissionMapper::toResponseDTO)
                        .collect(Collectors.toList())
        );
    }

    @Operation(summary = "Get all permissions for a specific role")
    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<RolePermissionResponseDTO>> getRolePermissionsByRoleId(
            @RequestHeader(GlobalConstant.HEADER_TENANT_ID) String tenantId,
            @PathVariable UUID roleId) {
        return ResponseEntity.ok(
                rolePermissionService.getRolePermissionsByRoleId(roleId).stream()
                        .map(RolePermissionMapper::toResponseDTO)
                        .collect(Collectors.toList())
        );
    }

    @Operation(summary = "Get all roles that have a specific permission")
    @GetMapping("/permission/{permissionId}")
    public ResponseEntity<List<RolePermissionResponseDTO>> getRolePermissionsByPermissionId(
            @RequestHeader(GlobalConstant.HEADER_TENANT_ID) String tenantId,
            @PathVariable UUID permissionId) {
        return ResponseEntity.ok(
                rolePermissionService.getRolePermissionsByPermissionId(permissionId).stream()
                        .map(RolePermissionMapper::toResponseDTO)
                        .collect(Collectors.toList())
        );
    }

    @Operation(summary = "Delete a role-permission mapping")
    @ApiResponse(responseCode = "204", description = "Role-permission mapping deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRolePermission(@PathVariable UUID id) {
        rolePermissionService.deleteRolePermission(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Check if a role-permission mapping exists")
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByRoleAndPermission(
            @RequestParam UUID roleId,
            @RequestParam UUID permissionId) {
        return ResponseEntity.ok(
                rolePermissionService.existsByRoleAndPermission(roleId, permissionId)
        );
    }

    @Operation(summary = "Check if a role has all specified permissions")
    @GetMapping("/has-all-permissions")
    public ResponseEntity<Boolean> hasAllPermissions(
            @RequestParam UUID roleId,
            @RequestParam List<UUID> permissionIds) {
        return ResponseEntity.ok(
                rolePermissionService.hasAllPermissions(roleId, permissionIds)
        );
    }
}