package com.lms.identityservice.Repository.Global;

import com.lms.identityservice.Model.Global.GlobalPermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GlobalPermissionRepository extends JpaRepository<GlobalPermissions, UUID> {
    Optional<GlobalPermissions> findByNameIgnoreCase(String name);

    List<GlobalPermissions> findByActionId(UUID actionId);

    List<GlobalPermissions> findByResourcesId(UUID resourceId);

    boolean existsByName(String name);

    boolean existsByActionIdAndResourcesId(UUID actionId, UUID resourceId);
}
