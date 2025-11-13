package com.lms.identityservice.Service.Global;

import com.lms.identityservice.Model.Global.GlobalRoles;

import java.util.List;
import java.util.UUID;

public interface GlobalRoleService {

    GlobalRoles createRole(GlobalRoles role);
    GlobalRoles getRoleById(UUID id);
    GlobalRoles getRoleByName(String name);
    List<GlobalRoles> getAllRoles();
    List<GlobalRoles> getTemplateRoles(boolean isTemplate);
    GlobalRoles updateRole(UUID id, GlobalRoles roleDetails);
    void deleteRole(UUID id);
    boolean roleExists(String name);
    GlobalRoles cloneRoleAsTemplate(UUID roleId, String newRoleName);
}
