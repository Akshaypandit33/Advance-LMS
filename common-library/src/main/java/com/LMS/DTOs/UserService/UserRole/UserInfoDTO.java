package com.LMS.DTOs.UserService.UserRole;

import java.util.UUID;

public record UserInfoDTO(
        UUID userId,
        String Name,
        String email
) {
}
