package com.lms.usermanagementservice.Repository;

import com.lms.usermanagementservice.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<Roles, UUID> {

    Optional<Roles> findByRoleName(String roleName);


    List<Roles> findByRoleNameIn(List<String> RoleNames);

    boolean existsByRoleName(String roleName);


}
