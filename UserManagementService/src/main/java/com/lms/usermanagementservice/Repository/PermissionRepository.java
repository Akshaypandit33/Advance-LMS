package com.lms.usermanagementservice.Repository;


import com.lms.usermanagementservice.Model.Actions;
import com.lms.usermanagementservice.Model.Permission;
import com.lms.usermanagementservice.Model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    Optional<Permission> findPermissionByActionsIdAndResourceId(Long actionsId, Long resourceId) ;
    boolean existsByActionsAndResourceAndTenantId(Actions actions, Resource resource, UUID tenantId);
}
