package com.lms.usermanagementservice.Mapper;

import com.LMS.DTOs.RolesDTO.RoleRequestDTO;
import com.LMS.DTOs.RolesDTO.RoleResponseDTO;
import com.lms.usermanagementservice.Model.Roles;

public class RoleMapper {

    public static RoleResponseDTO toResponseDTO(Roles roles) {
        return new RoleResponseDTO(roles.getId(),
                roles.getRoleName(),
                roles.getRoleDescription());
    }
}
