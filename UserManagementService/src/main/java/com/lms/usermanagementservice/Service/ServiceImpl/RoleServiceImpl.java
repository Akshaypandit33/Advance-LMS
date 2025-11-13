package com.lms.usermanagementservice.Service.ServiceImpl;

import com.LMS.Constants.GlobalConstant;
import com.LMS.DTOs.RolesDTO.RoleRequestDTO;
import com.LMS.DTOs.RolesDTO.RoleResponseDTO;
import com.LMS.Exceptions.RoleService.RoleAlreadyExistsException;
import com.lms.tenantcore.TenantContext;
import com.lms.usermanagementservice.BusinessLogic.Global_RoleBusinessLogic;
import com.lms.usermanagementservice.BusinessLogic.RoleBusinessLogic;
import com.lms.usermanagementservice.Mapper.RoleMapper;
import com.lms.usermanagementservice.Model.Globals.Global_Roles;
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
    private final Global_RoleBusinessLogic global_roleBusinessLogic;

    @Transactional
    @Override
    public RoleResponseDTO addRole(RoleRequestDTO roleRequestDTO) {
        if(global_roleBusinessLogic.isRoleExist(roleRequestDTO.roleName()))
        {
            throw new RoleAlreadyExistsException("You can not make roles same as global roles");
        }
        return RoleMapper.toResponseDTO(roleBusinessLogic.addRole(roleRequestDTO));
    }

    @Override
    public List<RoleResponseDTO> getAllRoles() {

        String currentTenant = TenantContext.getCurrentTenant();
        List<Global_Roles> globalRoles = null;
        try{
            TenantContext.setCurrentTenant(GlobalConstant.GLOBAL_METADATA);
            globalRoles = global_roleBusinessLogic.getAllGlobalRoles();
        }finally {
            TenantContext.setCurrentTenant(currentTenant);
        }
        List<Roles> rolesPage = roleBusinessLogic.findAllRoles();
        List<RoleResponseDTO> roleResponseDTOList = new ArrayList<>();
        for(Global_Roles globalRole : globalRoles){
            roleResponseDTOList.add(RoleMapper.toResponseDTO(globalRole));
        }
        for(Roles role : rolesPage){
            roleResponseDTOList.add(RoleMapper.toResponseDTO(role));
        }
        return roleResponseDTOList;
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
