package com.lms.usermanagementservice.Service.ServiceImpl;

import com.LMS.DTOs.UserService.UserRoleRequestDTO;
import com.LMS.DTOs.UserService.UserRoleResponseDTO;
import com.lms.usermanagementservice.Repository.UserRolesRepository;
import com.lms.usermanagementservice.Service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRolesRepository userRolesRepository;

    @Override
    public UserRoleResponseDTO assignUserRole(UserRoleRequestDTO userRoleRequestDTO) {
        return null;
    }
}
