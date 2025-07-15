package com.lms.usermanagementservice.Service;

import com.LMS.DTOs.UserService.UserRoleRequestDTO;
import com.LMS.DTOs.UserService.UserRoleResponseDTO;

public interface UserRoleService {
    UserRoleResponseDTO assignUserRole(UserRoleRequestDTO userRoleRequestDTO);
}
