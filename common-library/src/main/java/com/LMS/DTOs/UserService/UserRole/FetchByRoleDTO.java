package com.LMS.DTOs.UserService.UserRole;

import java.util.List;
import java.util.UUID;

public record FetchByRoleDTO(
        UUID roleId,
        String roleName,
        String descriptions,
        List<UserInfoDTO> users
) {
}
