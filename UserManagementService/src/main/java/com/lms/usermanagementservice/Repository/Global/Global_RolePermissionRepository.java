package com.lms.usermanagementservice.Repository.Global;

import com.lms.usermanagementservice.Model.Globals.Global_Permissions;
import com.lms.usermanagementservice.Model.Globals.Global_RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface Global_RolePermissionRepository extends JpaRepository<Global_RolePermission, UUID> {
    Optional<Global_RolePermission> findByRoleIdAndPermissionId(UUID roleId, UUID permissionId);

    boolean existsByPermission(Global_Permissions permission);
}
