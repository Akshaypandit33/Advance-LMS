package com.lms.usermanagementservice.Repository;

import com.lms.usermanagementservice.Model.Permission;
import com.lms.usermanagementservice.Model.RolePermission;
import com.lms.usermanagementservice.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, UUID> {
    Optional<RolePermission> findByRoleIdAndPermissionId(UUID roleId, UUID permissionId);

    Optional<RolePermission> findByPermission(Permission permission);
    boolean existsByPermission(Permission permission);
}
