package com.lms.identityservice.Service.Tenant.ServiceImpl;

import com.LMS.Exceptions.RoleService.RoleAlreadyExistsException;
import com.LMS.Exceptions.RoleService.RoleNotFoundException;
import com.lms.identityservice.Model.Tenant.Roles;
import com.lms.identityservice.Repository.Tenant.RolesRepository;
import com.lms.identityservice.Service.Tenant.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RolesRepository rolesRepository;


    @Override
    public Roles AddRoles(Roles roles) {

        if(roles.getRoleName() != null && !roles.getRoleName().isEmpty()) {
           if(existsRolesByName(roles.getRoleName())) {
               throw new RoleAlreadyExistsException("Role already exists");
           }
        }
        return rolesRepository.save(roles);
    }

    @Override
    public Roles getRoles(UUID id) {
        return rolesRepository.findById(id).orElseThrow(
                () -> new RoleNotFoundException("Role not found")
        );
    }

    @Override
    public Boolean existsRolesByName(String name) {
        return rolesRepository.existsByRoleNameIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    @Override
    public Roles getRolesByName(String name) {
        if(existsRolesByName(name)) {
            return rolesRepository.findByRoleNameIgnoreCase(name);
        }
        return null;
    }

    @Override
    public void deleteRolesByName(String name) {
        Roles roles = getRolesByName(name);
        if(roles != null) {
            rolesRepository.delete(roles);
        }else  {
            throw new RoleNotFoundException("Role not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }
}
