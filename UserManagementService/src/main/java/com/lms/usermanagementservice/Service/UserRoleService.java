package com.lms.usermanagementservice.Service;

import com.LMS.DTOs.UserService.UserRole.FetchByRoleDTO;
import com.LMS.DTOs.UserService.UserRole.UserRoleRequestDTO;
import com.LMS.DTOs.UserService.UserRole.UserRoleResponseDTO;
import com.LMS.DTOs.UserService.UserRole.UserRoleResponseDTOList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

public interface UserRoleService {
    UserRoleResponseDTO assignUserRole(UserRoleRequestDTO userRoleRequestDTO);
    UserRoleResponseDTOList listUserRoles(UUID userId);
     Map<String,String> revokeUserRole(UUID userId, String roleName);
     FetchByRoleDTO getUsersByRoleName(String roleName, Pageable pageable);
     Page<UserRoleResponseDTO> findAllUserRoles(Pageable pageable);
}
