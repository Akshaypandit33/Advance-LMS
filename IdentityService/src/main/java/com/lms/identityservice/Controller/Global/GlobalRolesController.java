package com.lms.identityservice.Controller.Global;

import com.LMS.DTOs.RolesDTO.RoleRequestDTO;
import com.LMS.DTOs.RolesDTO.RoleResponseDTO;
import com.lms.identityservice.Mapper.RoleMapper;
import com.lms.identityservice.Model.Global.GlobalRoles;
import com.lms.identityservice.Service.Global.GlobalRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/global-roles")
@RequiredArgsConstructor
public class GlobalRolesController {

    private final GlobalRoleService rolesService;


    @PostMapping
    public ResponseEntity<RoleResponseDTO> createRole(
            @Valid @RequestBody RoleRequestDTO request) {
        var role = RoleMapper.toGlobalRole(request);
        var createdRole = rolesService.createRole(role);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RoleMapper.toRoleResponseDTO(createdRole));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable UUID id) {
        var role = rolesService.getRoleById(id);
        return ResponseEntity.ok(RoleMapper.toRoleResponseDTO(role));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RoleResponseDTO> getRoleByName(@PathVariable String name) {
        var role = rolesService.getRoleByName(name);
        return ResponseEntity.ok(RoleMapper.toRoleResponseDTO(role));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        List<RoleResponseDTO> roles = rolesService.getAllRoles().stream()
                .map(RoleMapper::toRoleResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/templates")
    public ResponseEntity<List<RoleResponseDTO>> getTemplateRoles(
            @RequestParam(defaultValue = "true") boolean isTemplate) {
        List<RoleResponseDTO> roles = rolesService.getTemplateRoles(isTemplate).stream()
                .map(RoleMapper::toRoleResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(
            @PathVariable UUID id,
            @Valid @RequestBody RoleRequestDTO request) {
        var roleDetails = RoleMapper.toUpdatedGlobalRole(id, request);
        var updatedRole = rolesService.updateRole(id, roleDetails);
        return ResponseEntity.ok(RoleMapper.toRoleResponseDTO(updatedRole));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID id) {
        rolesService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/clone")
    public ResponseEntity<RoleResponseDTO> cloneRoleAsTemplate(
            @PathVariable UUID id,
            @RequestParam String newRoleName) {
        var clonedRole = rolesService.cloneRoleAsTemplate(id, newRoleName);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RoleMapper.toRoleResponseDTO(clonedRole));
    }
}