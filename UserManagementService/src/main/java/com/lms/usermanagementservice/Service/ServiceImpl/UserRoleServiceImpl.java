package com.lms.usermanagementservice.Service.ServiceImpl;

import com.LMS.DTOs.UserService.UserRoleRequestDTO;
import com.LMS.DTOs.UserService.UserRoleResponseDTO;
import com.lms.usermanagementservice.BusinessLogic.UserRoleBusinessLogic;

import com.lms.usermanagementservice.Mapper.UserRole.UserRoleEntityToResponseMapper;
import com.lms.usermanagementservice.Service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleBusinessLogic userRoleBusinessLogic;
    private final UserRoleEntityToResponseMapper userRoleEntityToResponseMapper;

    @Transactional
    @Override
    public UserRoleResponseDTO assignUserRole(UserRoleRequestDTO userRoleRequestDTO) {
        return userRoleEntityToResponseMapper.apply(
                userRoleBusinessLogic.assignUserRole(
                        userRoleRequestDTO.roleId(),
                        userRoleRequestDTO.userId()

                )
        );
    }
}
