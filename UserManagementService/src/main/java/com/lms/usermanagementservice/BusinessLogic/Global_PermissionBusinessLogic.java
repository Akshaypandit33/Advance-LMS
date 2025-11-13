package com.lms.usermanagementservice.BusinessLogic;

import com.LMS.DTOs.PermissionDTO.PermissionRequestDTO;
import com.LMS.Exceptions.ActionsService.ActionsNotFoundException;
import com.LMS.Exceptions.PermissionService.PermissionAlreadyExistsException;
import com.LMS.Exceptions.PermissionService.PermissionInUseException;
import com.LMS.Exceptions.PermissionService.PermissionNotFoundException;
import com.LMS.Exceptions.ResourceService.ResourceNotFoundException;
import com.lms.usermanagementservice.Model.Globals.Global_Actions;
import com.lms.usermanagementservice.Model.Globals.Global_Permissions;
import com.lms.usermanagementservice.Model.Globals.Global_Resources;
import com.lms.usermanagementservice.Repository.Global.Global_ActionsRepository;
import com.lms.usermanagementservice.Repository.Global.Global_PermissionRepository;
import com.lms.usermanagementservice.Repository.Global.Global_ResourceRepository;
import com.lms.usermanagementservice.Repository.Global.Global_RolePermissionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Global_PermissionBusinessLogic {

    private final Global_PermissionRepository permissionRepository;
    private final Global_ActionsRepository actionsRepository;
    private final Global_ResourceRepository resourceRepository;
    private final Global_RolePermissionRepository rolePermissionRepository;

    public Global_Permissions createPermission(PermissionRequestDTO dto, UUID tenantId) {

        Global_Actions action = actionsRepository.findById(dto.actionId())
                .orElseThrow(() -> new ActionsNotFoundException("Invalid action ID: " + dto.actionId()));

        Global_Resources resource = resourceRepository.findById(dto.resourceId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid resource ID: " + dto.resourceId()));

        // Prevent duplicate permission
        if (permissionRepository.existsByActionsAndResource(action, resource)) {
            throw new PermissionAlreadyExistsException("Permission already exists for action-resource combination.");
        }

        Global_Permissions permission = Global_Permissions.builder()
                .actions(action)
                .resource(resource)
                .build();

        return permissionRepository.save(permission);
    }
    // 2. Get all permissions
    public List<Global_Permissions> findAllPermissions() {
        return permissionRepository.findAll();
    }

    // 3. Find permission by ID
    public Global_Permissions findPermissionById(UUID id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException("Permission not found with ID: " + id));
    }

    // 4. delete permission
    public void deletePermissionById(UUID id) {
        Global_Permissions permission = findPermissionById(id);
        boolean exists = rolePermissionRepository.existsByPermission(permission);
        if (exists) {
            throw new PermissionInUseException("Cannot delete permission as it is assigned to one or more roles.");
        }
        permissionRepository.deleteById(id);
    }


}
