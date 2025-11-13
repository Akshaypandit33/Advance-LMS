package com.lms.identityservice.Repository.Tenant;

import com.lms.identityservice.Model.Tenant.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, UUID> {
    boolean existsByRole_IdAndPermissions_Id(UUID roleId, UUID permissionsId);

    List<RolePermission> findAllByRole_Id(UUID roleId);

    List<RolePermission> findAllByPermissions_Id(UUID permissionsId);

    long countByRole_IdAndPermissions_IdIn(UUID roleId, List<UUID> permissionIds);
}
