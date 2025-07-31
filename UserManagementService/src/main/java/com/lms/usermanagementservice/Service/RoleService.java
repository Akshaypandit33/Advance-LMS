package com.lms.usermanagementservice.Service;

import com.LMS.DTOs.RolesDTO.RoleRequestDTO;
import com.LMS.DTOs.RolesDTO.RoleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

public interface RoleService {

    RoleResponseDTO addRole(RoleRequestDTO roleRequestDTO);
    Page<RoleResponseDTO> getAllRoles(Pageable pageable);
    RoleResponseDTO getRole(UUID roleId);
    RoleResponseDTO findRoleByName(String roleName);
    Map<String,String> deleteRole(UUID roleId);

}
