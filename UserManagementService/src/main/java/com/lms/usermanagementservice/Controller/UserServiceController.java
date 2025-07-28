package com.lms.usermanagementservice.Controller;


import com.LMS.DTOs.UserService.UserRequestDTO;
import com.LMS.DTOs.UserService.UserResponseDTO;
import com.lms.tenantcore.TenantContext;
import com.lms.usermanagementservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserServiceController {

    private final UserService userService;
    // 1. Create user
    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserRequestDTO request) {
        return userService.createUser(request);
    }

    // 2. Update user by email (if you don't have userId)
    @PutMapping("/{email}")
    public UserResponseDTO updateUser(
            @PathVariable String email,
            @RequestBody UserRequestDTO request
    ) {
        return userService.updateUser(request, email);
    }

    // 3. Get all users (with pagination)
    @GetMapping
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userService.findAllUsers(pageable);
    }

    // 4. Search users (with pagination)
    @GetMapping("/search")
    public Page<UserResponseDTO> searchUsers(
            @RequestParam("q") String keyword,
            Pageable pageable
    ) {
        return userService.searchUsers(pageable, keyword);
    }

    // 5. Get user by ID
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable UUID id) {
        return userService.findUserById(id);
    }

    // 6. Get user by Email
    @GetMapping("/by-email/{email}")
    public UserResponseDTO getUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    // 7. Change user account status
    @PatchMapping("/{id}/status")
    public UserResponseDTO changeAccountStatus(
            @PathVariable UUID id,
            @RequestParam String status
    ) {
        return userService.changeAccountStatus(id, status);
    }

    // 8. Delete user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @GetMapping("/getHeaders")
    String getHeaders()
    {
        return TenantContext.getCurrentTenant();
    }

}
