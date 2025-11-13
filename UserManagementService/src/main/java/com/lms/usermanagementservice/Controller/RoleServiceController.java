package com.lms.usermanagementservice.Controller;

import com.LMS.DTOs.RolesDTO.RoleRequestDTO;
import com.LMS.DTOs.RolesDTO.RoleResponseDTO;
import com.lms.usermanagementservice.Service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/roles")
@RequiredArgsConstructor
public class RoleServiceController {

    private final RoleService roleService;

    /**
     * Add a new role
     */
    @PostMapping
    public ResponseEntity<RoleResponseDTO> addRole(
            @Valid @RequestBody RoleRequestDTO roleRequestDTO
    ) {
        RoleResponseDTO createdRole = roleService.addRole(roleRequestDTO);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    /**
     * Get paginated list of all roles
     */
    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    /**
     * Get role by ID
     */
    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable UUID roleId) {
        RoleResponseDTO role = roleService.getRole(roleId);
        return ResponseEntity.ok(role);
    }

    /**
     * Get role by name
     */
    @GetMapping("/search")
    public ResponseEntity<RoleResponseDTO> getRoleByName(@RequestParam String name) {
        RoleResponseDTO role = roleService.findRoleByName(name);
        return ResponseEntity.ok(role);
    }

    /**
     * Delete role by ID
     */
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Map<String, String>> deleteRole(@PathVariable UUID roleId) {
        Map<String, String> result = roleService.deleteRole(roleId);
        return ResponseEntity.ok(result);
    }
}

