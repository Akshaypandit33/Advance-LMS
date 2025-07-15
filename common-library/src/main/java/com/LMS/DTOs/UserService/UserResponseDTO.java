package com.LMS.DTOs.UserService;

import com.LMS.Constants.AccountStatus;

import java.util.List;
import java.util.UUID;

public record UserResponseDTO(
        UUID uuid,
        UUID tenantId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String gender,
        AccountStatus accountStatus,
        List<String> roles
) {
}
