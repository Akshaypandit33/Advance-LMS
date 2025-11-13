package com.lms.identityservice.Mapper;

import com.LMS.DTOs.PermissionDTO.PermissionRequestDTO;
import com.LMS.DTOs.PermissionDTO.PermissionResponseDTO;
import com.lms.identityservice.Model.Global.GlobalPermissions;

import java.security.Permission;

public class PermissionMapper {

    public static PermissionResponseDTO toPermissionResponseDTO(GlobalPermissions permission) {
        return new PermissionResponseDTO(
                permission.getId(),
                permission.getName(),
                permission.getDescription(),
                ActionMapper.toActionResponseDTO(permission.getAction()),
                ResourceMapper.toeResourceResponseDTO(permission.getResources())
        );
    }

}
