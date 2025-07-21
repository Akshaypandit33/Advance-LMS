package com.lms.usermanagementservice.Mapper;

import com.LMS.DTOs.PermissionDTO.PermissionRequestDTO;
import com.lms.usermanagementservice.Model.Permission;

public class PermissionMapper {

    public static Permission toModel(PermissionRequestDTO permissionRequestDTO) {
        return Permission.builder()
                .build();
    }
}
