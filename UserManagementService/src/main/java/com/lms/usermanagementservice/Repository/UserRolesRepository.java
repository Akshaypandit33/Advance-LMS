package com.lms.usermanagementservice.Repository;

import com.lms.usermanagementservice.Model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRole, UUID> {
    Optional<UserRole> findUserRoleByRole_IdAndUser_Id(UUID role_Id, UUID user_Id);

    // super admin
    @Query("SELECT ur from UserRole ur join ur.user u join ur.role r where u.id= :userId AND r.id= :roleId")
    Optional<UserRole>findByUserIdAndRoleIdForSuperAdmin(@Param("userId") UUID userId, @Param("roleId") UUID roleId);
}
