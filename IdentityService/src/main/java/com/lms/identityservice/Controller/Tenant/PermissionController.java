package com.lms.identityservice.Controller.Tenant;

import com.LMS.DTOs.PermissionDTO.PermissionResponseDTO;
import com.lms.identityservice.Mapper.PermissionMapper;
import com.lms.identityservice.Service.Global.GlobalPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/permissions")
public class PermissionController {
    private final GlobalPermissionService permissionsService;
    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> getPermissionById(@PathVariable UUID id) {
        var permission = permissionsService.getPermissionById(id);
        return ResponseEntity.ok(PermissionMapper.toPermissionResponseDTO(permission));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PermissionResponseDTO> getPermissionByName(@PathVariable String name) {
        var permission = permissionsService.getPermissionByName(name);
        return ResponseEntity.ok(PermissionMapper.toPermissionResponseDTO(permission));
    }

    @GetMapping
    public ResponseEntity<List<PermissionResponseDTO>> getAllPermissions() {
        var permissions = permissionsService.getAllPermissions().stream()
                .map(PermissionMapper::toPermissionResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/action/{actionId}")
    public ResponseEntity<List<PermissionResponseDTO>> getPermissionsByAction(
            @PathVariable UUID actionId) {
        var permissions = permissionsService.getPermissionsByAction(actionId).stream()
                .map(PermissionMapper::toPermissionResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<PermissionResponseDTO>> getPermissionsByResource(
            @PathVariable UUID resourceId) {
        var permissions = permissionsService.getPermissionsByResource(resourceId).stream()
                .map(PermissionMapper::toPermissionResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(permissions);
    }

}
