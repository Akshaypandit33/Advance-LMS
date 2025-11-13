package com.lms.identityservice.Repository.Tenant;

import com.lms.identityservice.Model.Tenant.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<Roles, UUID> {
    boolean existsByRoleNameIgnoreCase(String roleName);

    Roles findByRoleNameIgnoreCase(String roleName);
}
