package com.lms.identityservice.Repository.Global;

import com.lms.identityservice.Model.Global.GlobalRolePermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GlobalRolePermissionRepository extends JpaRepository<GlobalRolePermissions, UUID> {
    List<GlobalRolePermissions> findByRolesId(UUID roleId);

    List<GlobalRolePermissions> findByPermissionsId(UUID permissionId);

    boolean existsByRolesIdAndPermissionsId(UUID roleId, UUID permissionId);
}
