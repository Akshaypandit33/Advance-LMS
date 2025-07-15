package com.LMS.DTOs.UserService;

import com.LMS.Constants.AccountStatus;

import java.util.List;

public record UserRequestDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        String phoneNumber,
        AccountStatus accountStatus,
        List<String> roles
) {
}
