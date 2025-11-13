package com.lms.identityservice.Service.Global.Impl;

import com.LMS.Exceptions.RoleService.RoleAlreadyExistsException;
import com.LMS.Exceptions.RoleService.RoleNotFoundException;
import com.lms.identityservice.Model.Global.GlobalRoles;

import com.lms.identityservice.Repository.Global.GlobalRoleRepository;
import com.lms.identityservice.Service.Global.GlobalRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class GlobalRoleServiceImpl implements GlobalRoleService {

    private final GlobalRoleRepository rolesRepository;


    @Override
    public GlobalRoles createRole(GlobalRoles role) {
        if (roleExists(role.getName())) {
            throw new IllegalArgumentException("Role with name " + role.getName() + " already exists");
        }
        return rolesRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public GlobalRoles getRoleById(UUID id) {
        return rolesRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public GlobalRoles getRoleByName(String name) {
        return rolesRepository.findByName(name.toUpperCase())
                .orElseThrow(() -> new RoleNotFoundException("Role not found with name: " + name));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GlobalRoles> getAllRoles() {
        return rolesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GlobalRoles> getTemplateRoles(boolean isTemplate) {
        return rolesRepository.findByTemplateStatus(isTemplate);
    }

    @Override
    public GlobalRoles updateRole(UUID id, GlobalRoles roleDetails) {
        GlobalRoles role = getRoleById(id);

        if (roleDetails.getName() != null && !roleDetails.getName().equals(role.getName())) {
            if (roleExists(roleDetails.getName())) {
                throw new RoleAlreadyExistsException("Role with name " + roleDetails.getName() + " already exists");
            }
            role.setName(roleDetails.getName());
        }

        if (roleDetails.getDescription() != null) {
            role.setDescription(roleDetails.getDescription());
        }

        role.setTemplate(roleDetails.isTemplate());

        return rolesRepository.save(role);
    }

    @Override
    public void deleteRole(UUID id) {
        GlobalRoles role = getRoleById(id);
        rolesRepository.delete(role);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean roleExists(String name) {
        return rolesRepository.existsByName(name.toUpperCase());
    }

    @Override
    public GlobalRoles cloneRoleAsTemplate(UUID roleId, String newRoleName) {
        GlobalRoles existingRole = getRoleById(roleId);

        GlobalRoles newRole = GlobalRoles.builder()
                .name(newRoleName)
                .Description(existingRole.getDescription())
                .isTemplate(true)
                .build();

        return createRole(newRole);
    }
}