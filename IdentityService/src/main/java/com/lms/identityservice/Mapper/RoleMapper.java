package com.lms.identityservice.Mapper;

import com.LMS.DTOs.RolesDTO.RoleRequestDTO;
import com.LMS.DTOs.RolesDTO.RoleResponseDTO;
import com.lms.identityservice.Model.Global.GlobalRoles;
import com.lms.identityservice.Model.Tenant.Roles;

import java.util.UUID;

public class RoleMapper {

    public static RoleResponseDTO toRoleResponseDTO(GlobalRoles role) {
        return new RoleResponseDTO(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.isTemplate()
        );
    }

    public static GlobalRoles toGlobalRole(RoleRequestDTO request) {
        return GlobalRoles.builder()
                .name(request.roleName())
                .Description(request.descriptions())
                .build();
    }

    public static GlobalRoles toUpdatedGlobalRole(UUID id, RoleRequestDTO request) {
        return GlobalRoles.builder()
                .id(id)
                .name(request.roleName())
                .Description(request.descriptions())
                .isTemplate(request.isTemplate())
                .build();
    }
    public static RoleResponseDTO toRoleResponseDTO(Roles role) {
        return new RoleResponseDTO(
                role.getId(),
                role.getRoleName(),
                role.getRoleDescription(),
                false
        );
    }
}
