package com.lms.usermanagementservice.BusinessLogic;

import com.lms.usermanagementservice.Model.Globals.Global_Roles;
import com.lms.usermanagementservice.Repository.Global.Global_RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Global_RoleBusinessLogic {

    private final Global_RolesRepository global_rolesRepository;

    public List<Global_Roles> getAllGlobalRoles(){
        return global_rolesRepository.findAll();
    }

    public boolean isRoleExist(String roleName){
        return global_rolesRepository.existsByRoleName(roleName.toUpperCase());
    }
}
