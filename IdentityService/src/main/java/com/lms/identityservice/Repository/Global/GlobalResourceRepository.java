package com.lms.identityservice.Repository.Global;

import com.lms.identityservice.Model.Global.GlobalResources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GlobalResourceRepository extends JpaRepository<GlobalResources, UUID> {

    List<GlobalResources> findGlobalResourcesByResourceTypeIgnoreCase(String resourceType);

    Optional<GlobalResources> findByNameIgnoreCase(String upperCase);

    boolean existsByName(String name);
}
