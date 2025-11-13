package com.lms.usermanagementservice.Repository.Global;

import com.LMS.Constants.ResourcesName;
import com.lms.usermanagementservice.Model.Globals.Global_Resources;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface Global_ResourceRepository extends JpaRepository<Global_Resources, Long> {

    Optional<Global_Resources> findByResourceName(ResourcesName resourceName);

    List<Global_Resources> findByResourceNameIn(Collection<ResourcesName> resourceNames);

    boolean existsByResourceName(ResourcesName resourceName);
}
