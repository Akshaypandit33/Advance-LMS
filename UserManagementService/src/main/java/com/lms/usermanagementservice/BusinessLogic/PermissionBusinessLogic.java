package com.lms.usermanagementservice.BusinessLogic;

import com.LMS.DTOs.PermissionDTO.PermissionRequestDTO;
import com.LMS.Exceptions.ActionsService.ActionsNotFoundException;
import com.LMS.Exceptions.PermissionService.PermissionAlreadyExistsException;
import com.LMS.Exceptions.PermissionService.PermissionInUseException;
import com.LMS.Exceptions.PermissionService.PermissionNotFoundException;
import com.LMS.Exceptions.ResourceService.ResourceNotFoundException;
import com.lms.usermanagementservice.Model.Actions;
import com.lms.usermanagementservice.Model.Permission;
import com.lms.usermanagementservice.Model.Resource;
import com.lms.usermanagementservice.Model.RolePermission;
import com.lms.usermanagementservice.Repository.ActionsRepository;
import com.lms.usermanagementservice.Repository.PermissionRepository;
import com.lms.usermanagementservice.Repository.ResourceRepository;
import com.lms.usermanagementservice.Repository.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionBusinessLogic {

    private final PermissionRepository permissionRepository;
    private final ActionsRepository actionsRepository;
    private final ResourceRepository resourceRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public Permission createPermission(PermissionRequestDTO dto, UUID tenantId) {

        Actions action = actionsRepository.findById(dto.actionId())
                .orElseThrow(() -> new ActionsNotFoundException("Invalid action ID: " + dto.actionId()));

        Resource resource = resourceRepository.findById(dto.resourceId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid resource ID: " + dto.resourceId()));

        // Prevent duplicate permission
        if (permissionRepository.existsByActionsAndResource(action, resource)) {
            throw new PermissionAlreadyExistsException("Permission already exists for action-resource combination.");
        }

        Permission permission = Permission.builder()
                .actions(action)
                .resource(resource)
                .build();

        return permissionRepository.save(permission);
    }
    // 2. Get all permissions
    public List<Permission> findAllPermissions() {
        return permissionRepository.findAll();
    }

    // 3. Find permission by ID
    public Permission findPermissionById(UUID id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException("Permission not found with ID: " + id));
    }

    // 4. delete permission

    public void deletePermissionById(UUID id) {
        Permission permission = findPermissionById(id);
        boolean exists = rolePermissionRepository.existsByPermission(permission);
        if (exists) {
            throw new PermissionInUseException("Cannot delete permission as it is assigned to one or more roles.");
        }
        permissionRepository.deleteById(id);
    }


}
