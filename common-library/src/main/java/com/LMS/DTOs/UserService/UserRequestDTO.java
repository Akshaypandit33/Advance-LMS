package com.LMS.DTOs.UserService;

import com.LMS.Constants.AccountStatus;

import java.util.List;
import java.util.UUID;

public record UserRequestDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        String phoneNumber,
        String gender,
        AccountStatus accountStatus,
        List<UUID> rolesId
) {
}
