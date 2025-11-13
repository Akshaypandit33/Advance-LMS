package com.lms.usermanagementservice.Controller;

import com.LMS.DTOs.DeleteResourceDTO;
import com.LMS.DTOs.UserService.UserRole.FetchByRoleDTO;
import com.LMS.DTOs.UserService.UserRole.UserRoleRequestDTO;
import com.LMS.DTOs.UserService.UserRole.UserRoleResponseDTO;
import com.LMS.DTOs.UserService.UserRole.UserRoleResponseDTOList;
import com.lms.usermanagementservice.Service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/user-roles")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    // Assign Role to User
    @PostMapping
    public ResponseEntity<UserRoleResponseDTO> assignUserRole(@RequestBody UserRoleRequestDTO request) {
        UserRoleResponseDTO response = userRoleService.assignUserRole(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // List Roles of a User
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserRoleResponseDTOList> getUserRoles(@PathVariable UUID userId) {
        UserRoleResponseDTOList response = userRoleService.listUserRoles(userId);
        return ResponseEntity.ok(response);
    }

    // Revoke Role from User
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<DeleteResourceDTO> revokeUserRole(
            @PathVariable UUID userId,
            @RequestParam String roleName
    ) {

        return ResponseEntity.ok( userRoleService.revokeUserRole(userId, roleName));
    }

    // Get All Users by Role Name (for Admin UI)
    @GetMapping("/role/{roleName}")
    public ResponseEntity<FetchByRoleDTO> getUsersByRoleName(
            @PathVariable String roleName,
            Pageable pageable
    ) {
        FetchByRoleDTO response = userRoleService.getUsersByRoleName(roleName, pageable);
        return ResponseEntity.ok(response);
    }

    // get All userRoles
    @GetMapping("/all")
    public ResponseEntity<Page<UserRoleResponseDTO>> getAllUserRoles(Pageable pageable) {
        return ResponseEntity.ok(userRoleService.findAllUserRoles(pageable));
    }

}

