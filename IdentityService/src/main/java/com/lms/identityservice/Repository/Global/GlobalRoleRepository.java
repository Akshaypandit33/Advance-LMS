package com.lms.identityservice.Repository.Global;

import com.lms.identityservice.Model.Global.GlobalRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GlobalRoleRepository extends JpaRepository<GlobalRoles, UUID> {
    Optional<GlobalRoles> findByName(String roleName);

    @Query("SELECT r FROM GlobalRoles r WHERE r.isTemplate = :isTemplate")
    List<GlobalRoles> findByTemplateStatus(@Param("isTemplate") boolean isTemplate);


    boolean existsByName(String roleName);
}
