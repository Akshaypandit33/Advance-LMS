package com.lms.usermanagementservice.Repository.Global;

import com.lms.usermanagementservice.Model.Globals.Global_Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface Global_RolesRepository extends JpaRepository<Global_Roles, UUID> {
    List<Global_Roles> findByRoleNameIn(List<String> defaultRoleNames);

    boolean existsByRoleName(String roleName);
}
