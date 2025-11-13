package com.lms.usermanagementservice.Repository.Global;

import com.lms.usermanagementservice.Model.Globals.Global_Actions;
import com.lms.usermanagementservice.Model.Globals.Global_Permissions;
import com.lms.usermanagementservice.Model.Globals.Global_Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface Global_PermissionRepository extends JpaRepository<Global_Permissions, UUID> {
    Optional<Global_Permissions> findPermissionByActionsIdAndResourceId(Long actionId, Long resourceId);

    boolean existsByActionsAndResource( Global_Actions action, Global_Resources resource);
}
