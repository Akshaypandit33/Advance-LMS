package com.lms.usermanagementservice.Repository;

import com.lms.usermanagementservice.Model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRole, UUID> {
    Optional<UserRole> findUserRoleByRole_IdAndUser_Id(UUID role_Id, UUID user_Id);

    Optional<UserRole> findUserRoleByRole_RoleNameAndUser_Id(String roleName, UUID user_Id);

    List<UserRole> findUserRolesByUser_Id(UUID user_Id);

    Page<UserRole> findUserRolesByRole_RoleName(String roleName, Pageable pageable);
}
