package com.lms.usermanagementservice.Repository;

import com.LMS.Constants.ACTIONS;
import com.lms.usermanagementservice.Model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {

}
