package com.lms.usermanagementservice.Service.ServiceImpl;

import com.LMS.DTOs.RolesDTO.RoleRequestDTO;
import com.LMS.DTOs.RolesDTO.RoleResponseDTO;
import com.lms.usermanagementservice.BusinessLogic.RoleBusinessLogic;
import com.lms.usermanagementservice.Mapper.RoleMapper;
import com.lms.usermanagementservice.Model.Roles;
import com.lms.usermanagementservice.Model.UserRole;
import com.lms.usermanagementservice.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleBusinessLogic roleBusinessLogic;

    @Transactional
    @Override
    public RoleResponseDTO addRole(RoleRequestDTO roleRequestDTO) {
        return RoleMapper.toResponseDTO(roleBusinessLogic.addRole(roleRequestDTO));
    }

    @Override
    public Page<RoleResponseDTO> getAllRoles(Pageable pageable) {

        Page<Roles> rolesPage = roleBusinessLogic.findAllRoles(pageable);

        return rolesPage.map(RoleMapper::toResponseDTO);
    }

    @Override
    public RoleResponseDTO getRole(UUID roleId) {
        return RoleMapper.toResponseDTO(roleBusinessLogic.getRoleById(roleId));
    }

    @Override
    public RoleResponseDTO findRoleByName(String roleName) {
        return RoleMapper.toResponseDTO(roleBusinessLogic.findRoleByName(
                roleName
        ));
    }

    @Override
    public Map<String, String> deleteRole(UUID roleId) {
        return roleBusinessLogic.deleteRole(roleId);
    }
}
