package com.lms.identityservice.Service.Global.Impl;



import com.LMS.DTOs.PermissionDTO.PermissionRequestDTO;
import com.LMS.Exceptions.ResourceService.ResourceNotFoundException;
import com.lms.identityservice.Model.Global.GlobalActions;
import com.lms.identityservice.Model.Global.GlobalPermissions;
import com.lms.identityservice.Model.Global.GlobalResources;

import com.lms.identityservice.Repository.Global.GlobalActionsRepository;

import com.lms.identityservice.Repository.Global.GlobalPermissionRepository;
import com.lms.identityservice.Repository.Global.GlobalResourceRepository;
import com.lms.identityservice.Service.Global.GlobalPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class GlobalPermissionsServiceImpl implements GlobalPermissionService {

    private final GlobalPermissionRepository permissionsRepository;
    private final GlobalActionsRepository actionsRepository;
    private final GlobalResourceRepository resourcesRepository;



    @Override
    public GlobalPermissions createPermission(PermissionRequestDTO permission) {
        // Validate action and resource exist
        GlobalActions action = actionsRepository.findById(permission.actionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Action not found with id: " + permission.actionId()));

        GlobalResources resource = resourcesRepository.findById(permission.resourceId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with id: " + permission.resourceId()));

        // Check if permission already exists for this action-resource combination
        if (permissionExistsForActionAndResource(action.getId(), resource.getId())) {
            throw new IllegalArgumentException(
                    "Permission already exists for action " + action.getName() +
                            " and resource " + resource.getName());
        }
        GlobalPermissions newPermission = new GlobalPermissions();
        newPermission.setAction(action);
        newPermission.setResources(resource);

        return permissionsRepository.save( newPermission);
    }

    @Override
    @Transactional(readOnly = true)
    public GlobalPermissions getPermissionById(UUID id) {
        return permissionsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public GlobalPermissions getPermissionByName(String name) {
        return permissionsRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with name: " + name));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GlobalPermissions> getAllPermissions() {
        return permissionsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GlobalPermissions> getPermissionsByAction(UUID actionId) {
        if (!actionsRepository.existsById(actionId)) {
            throw new ResourceNotFoundException("Action not found with id: " + actionId);
        }
        return permissionsRepository.findByActionId(actionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GlobalPermissions> getPermissionsByResource(UUID resourceId) {
        if (!resourcesRepository.existsById(resourceId)) {
            throw new ResourceNotFoundException("Resource not found with id: " + resourceId);
        }
        return permissionsRepository.findByResourcesId(resourceId);
    }


    @Override
    public void deletePermission(UUID id) {
        GlobalPermissions permission = getPermissionById(id);
        permissionsRepository.delete(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean permissionExists(String name) {
        return permissionsRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean permissionExistsForActionAndResource(UUID actionId, UUID resourceId) {
        return permissionsRepository.existsByActionIdAndResourcesId(actionId, resourceId);
    }
}
