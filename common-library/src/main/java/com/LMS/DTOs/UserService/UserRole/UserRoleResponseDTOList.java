package com.LMS.DTOs.UserService.UserRole;

import java.util.List;
import java.util.UUID;

public record UserRoleResponseDTOList(
        UUID userId,
        String fullName,
        List<UserRoleInfoDTO> roles
) {}

