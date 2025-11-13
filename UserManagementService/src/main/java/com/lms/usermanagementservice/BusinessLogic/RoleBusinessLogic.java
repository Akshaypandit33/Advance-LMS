package com.lms.usermanagementservice.BusinessLogic;

import com.LMS.DTOs.RolesDTO.RoleRequestDTO;
import com.LMS.Exceptions.RoleService.RoleAlreadyExistsException;
import com.LMS.Exceptions.RoleService.RoleNotFoundException;
import com.lms.usermanagementservice.Model.Roles;
import com.lms.usermanagementservice.Repository.Global.Global_RolesRepository;
import com.lms.usermanagementservice.Repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class RoleBusinessLogic {

    private final RolesRepository rolesRepository;
    public Roles addRole(RoleRequestDTO roleRequestDTO) {
        boolean exists = rolesRepository.existsByRoleName(roleRequestDTO.roleName().toUpperCase());
        if (exists) {
            throw new RoleAlreadyExistsException("Role Already Exists");
        }

        return rolesRepository.save(Roles.builder()
                .roleName(roleRequestDTO.roleName())
                        .roleDescription(roleRequestDTO.descriptions())
                .build());
    }

    public List<Roles> findAllRoles() {

        return rolesRepository.findAll();
    }

    public Roles getRoleById( UUID roleId) {
        Roles role = rolesRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        return role;
    }

   public Map<String,String> deleteRole(UUID roleId) {
        Roles role = getRoleById(roleId);
        rolesRepository.deleteById(roleId);
        Map<String,String> map = new HashMap<>();
        map.put("status","success");
        map.put("roleName","Role - "+role.getRoleName()+"deleted successfully");
        return map;
    }

    public Roles findRoleByName(String roleName) {
        return rolesRepository.findByRoleName(roleName.toUpperCase())
                .orElseThrow(
                        () -> new RoleNotFoundException("Role not found")
                );
    }
}
