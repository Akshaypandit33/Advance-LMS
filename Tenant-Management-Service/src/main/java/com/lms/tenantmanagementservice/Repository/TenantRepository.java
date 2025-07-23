package com.lms.tenantmanagementservice.Repository;

import com.lms.tenantmanagementservice.Model.Tenants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<Tenants, UUID> {
    void deleteTenantsById(UUID tenantId);

    Optional<Tenants> findByCollegeCode(String upperCase);
}
