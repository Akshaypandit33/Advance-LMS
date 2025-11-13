package com.lms.identityservice.Service.Tenant;

import com.lms.identityservice.Model.Tenant.Roles;


import java.util.List;
import java.util.UUID;

public interface RoleService {
    Roles AddRoles(Roles roles);
    Roles getRoles(UUID id);
    Boolean existsRolesByName(String name);
    Roles getRolesByName(String name);
    void deleteRolesByName(String name);
    List<Roles> getAllRoles();

}
