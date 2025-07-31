package com.lms.usermanagementservice.Service.ServiceImpl;

import com.LMS.DTOs.UserService.UserRole.FetchByRoleDTO;
import com.LMS.DTOs.UserService.UserRole.UserRoleRequestDTO;
import com.LMS.DTOs.UserService.UserRole.UserRoleResponseDTO;
import com.LMS.DTOs.UserService.UserRole.UserRoleResponseDTOList;
import com.lms.usermanagementservice.BusinessLogic.UserRoleBusinessLogic;

import com.lms.usermanagementservice.Mapper.UserRole.UserRoleEntityToResponseMapper;
import com.lms.usermanagementservice.Model.UserRole;
import com.lms.usermanagementservice.Service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleBusinessLogic userRoleBusinessLogic;
    private final UserRoleEntityToResponseMapper mapper;

    @Transactional
    @Override
    public UserRoleResponseDTO assignUserRole(UserRoleRequestDTO userRoleRequestDTO) {
        return mapper.ToResponseDTO(
                userRoleBusinessLogic.assignUserRole(
                        userRoleRequestDTO.roleName(),
                        userRoleRequestDTO.userId()
                )
        );
    }

    @Override
    public UserRoleResponseDTOList listUserRoles(UUID userId) {
        return mapper.ListToResponseDTO(userRoleBusinessLogic.findUserRolesByUser_Id(userId));
    }

    @Transactional
    @Override
    public Map<String,String> revokeUserRole(UUID userId, String roleName) {
        return userRoleBusinessLogic.revokeUserRole(userId, roleName);
    }

    @Override
    public FetchByRoleDTO getUsersByRoleName(String roleName, Pageable pageable) {
        Page<UserRole> userRoles = userRoleBusinessLogic.findUsersByRole(roleName, pageable);

        return mapper.toFetchByRoleDTO(userRoles);
    }

    @Override
    public Page<UserRoleResponseDTO> findAllUserRoles(Pageable pageable) {
        Page<UserRole> userRoles = userRoleBusinessLogic.findAllUserRoles(pageable);
        return userRoles.map(mapper::ToResponseDTO);
    }
}
