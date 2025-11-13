package com.lms.identityservice.Controller.Tenant;

import com.LMS.DTOs.RolesDTO.RoleRequestDTO;
import com.LMS.DTOs.RolesDTO.RoleResponseDTO;
import com.lms.identityservice.Model.Tenant.Roles;
import com.lms.identityservice.Service.Global.GlobalRoleService;
import com.lms.identityservice.Service.Tenant.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/roles")
public class RolesController {

    private final RoleService roleService;
    private final GlobalRoleService globalRoleService; // Assuming this is for global roles logic

    // --- Mappers (Good practice to keep them private) ---

    private RoleResponseDTO toRoleResponseDTO(Roles roles){
        // Assuming 'isGlobal' logic is handled elsewhere or is defaulted to false for tenant roles
        return new RoleResponseDTO(roles.getId(), roles.getRoleName(), roles.getRoleDescription(), false);
    }

    private Roles toRoles(RoleRequestDTO roleRequestDTO){
        return Roles.builder()
                .roleName(roleRequestDTO.roleName())
                .roleDescription(roleRequestDTO.descriptions()) // Corrected typo here
                .build();
    }

    // --- CRUD Endpoints ---

    // POST /v1/roles
    @PostMapping
    public ResponseEntity<RoleResponseDTO>  createRole(@Valid @RequestBody RoleRequestDTO roleRequestDTO) {
        // Optional: Add a check to prevent duplicate role creation
        if (roleService.existsRolesByName(roleRequestDTO.roleName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role with name " + roleRequestDTO.roleName() + " already exists.");
        }

        Roles roles = roleService.AddRoles(toRoles(roleRequestDTO));
        return new ResponseEntity<>(toRoleResponseDTO(roles), HttpStatus.CREATED);
    }

    // GET /v1/roles
    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        List<Roles> list = roleService.getAllRoles();
        List<RoleResponseDTO> roleResponseDTO = new ArrayList<>();

        // Use stream mapping for cleaner code (optional)
        list.stream().map(this::toRoleResponseDTO).forEach(roleResponseDTO::add);

        return new ResponseEntity<>(roleResponseDTO, HttpStatus.OK);
    }

    // GET /v1/roles/{id}
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable UUID id) {
        Roles roles = roleService.getRoles(id);

        if (roles == null) {
            // Use standard exception for "Not Found"
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found with ID: " + id);
        }

        return new ResponseEntity<>(toRoleResponseDTO(roles), HttpStatus.OK);
    }

    // PUT /v1/roles/{id}
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable UUID id, @Valid @RequestBody RoleRequestDTO roleRequestDTO) {
        Roles existingRole = roleService.getRoles(id);

        if (existingRole == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found with ID: " + id);
        }

        // Update fields (assuming you only update name/description)
        Roles updatedRoles = Roles.builder()
                .id(id) // Ensure ID is maintained for update
                .roleName(roleRequestDTO.roleName())
                .roleDescription(roleRequestDTO.descriptions())
                .build();

        Roles savedRoles = roleService.AddRoles(updatedRoles); // Assuming AddRoles handles both create and update

        return new ResponseEntity<>(toRoleResponseDTO(savedRoles), HttpStatus.OK);
    }

    // DELETE /v1/roles/{name}
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteRoleByName(@PathVariable String name) {
        if (!roleService.existsRolesByName(name)) {
            // It's generally better practice to return 204 No Content even if the resource doesn't exist
            // for idempotent DELETE operations, but a 404 is also acceptable if you want strict checking.
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found with name: " + name);
        }
        roleService.deleteRolesByName(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 indicates successful deletion or nothing to delete
    }

    // --- Utility Endpoints ---

    // GET /v1/roles/exists/{name} (Useful for client-side validation)
    @GetMapping("/exists/{name}")
    public ResponseEntity<Boolean> existsRoleByName(@PathVariable String name) {
        boolean exists = roleService.existsRolesByName(name);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}