package com.lms.usermanagementservice.Repository;

import com.LMS.Constants.ResourcesName;
import com.lms.usermanagementservice.Model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Optional<Resource> findByResourceName(ResourcesName resourceName);

    List<Resource> findByResourceNameIn(Collection<ResourcesName> resourceNames);

    boolean existsByResourceName(ResourcesName resourceName);
}
