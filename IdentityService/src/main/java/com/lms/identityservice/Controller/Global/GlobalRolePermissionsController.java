package com.lms.identityservice.Controller.Global;

import com.LMS.DTOs.RolePermissionDTO.RolePermissionRequestDTO;
import com.LMS.DTOs.RolePermissionDTO.RolePermissionResponseDTO;
import com.lms.identityservice.Mapper.RolePermissionMapper;
import com.lms.identityservice.Service.Global.GlobalRolePermissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/role-permissions")
@RequiredArgsConstructor
public class GlobalRolePermissionsController {

    private final GlobalRolePermissionsService globalRolePermissionsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RolePermissionResponseDTO createRolePermission(@RequestBody RolePermissionRequestDTO request) {
        var rolePermission = globalRolePermissionsService.createRolePermission(request.roleId(), request.permissionId());
        return RolePermissionMapper.toResponseDTO(rolePermission);
    }

    @GetMapping("/{id}")
    public RolePermissionResponseDTO getRolePermission(@PathVariable UUID id) {
        return RolePermissionMapper.toResponseDTO(globalRolePermissionsService.getRolePermissionById(id));
    }

    @GetMapping
    public List<RolePermissionResponseDTO> getAllRolePermissions() {
        return globalRolePermissionsService.getAllRolePermissions().stream()
                .map(RolePermissionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/role/{roleId}")
    public List<RolePermissionResponseDTO> getRolePermissionsByRole(@PathVariable UUID roleId) {
        return globalRolePermissionsService.getRolePermissionsByRoleId(roleId).stream()
                .map(RolePermissionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/permission/{permissionId}")
    public List<RolePermissionResponseDTO> getRolePermissionsByPermission(@PathVariable UUID permissionId) {
        return globalRolePermissionsService.getRolePermissionsByPermissionId(permissionId).stream()
                .map(RolePermissionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRolePermission(@PathVariable UUID id) {
        globalRolePermissionsService.deleteRolePermission(id);
    }
}