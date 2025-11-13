package com.lms.identityservice.Mapper;

import com.LMS.DTOs.RolePermissionDTO.RolePermissionResponseDTO;
import com.lms.identityservice.Model.Global.GlobalRolePermissions;
import com.lms.identityservice.Model.Tenant.RolePermission;

public class RolePermissionMapper {
    public static RolePermissionResponseDTO toResponseDTO(GlobalRolePermissions gr) {
      return new RolePermissionResponseDTO(
              gr.getId(),
              RoleMapper.toRoleResponseDTO(gr.getRoles()),
              PermissionMapper.toPermissionResponseDTO(gr.getPermissions())
      )  ;
    };

    public static RolePermissionResponseDTO toResponseDTO(RolePermission rolePermission) {
        return new RolePermissionResponseDTO(
                rolePermission.getId(),
                RoleMapper.toRoleResponseDTO(rolePermission.getRole()),
                PermissionMapper.toPermissionResponseDTO(rolePermission.getPermissions())
        );
    }
}
