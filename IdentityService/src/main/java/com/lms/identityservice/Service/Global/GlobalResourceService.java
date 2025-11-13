package com.lms.identityservice.Service.Global;


import com.lms.identityservice.Model.Global.GlobalResources;

import java.util.List;
import java.util.UUID;

public interface GlobalResourceService {
    GlobalResources createResource(GlobalResources resource);
    GlobalResources getResourceById(UUID id);
    GlobalResources getResourceByName(String name);
    List<GlobalResources> getAllResources();
    List<GlobalResources> getResourcesByType(String resourceType);
    GlobalResources updateResource(UUID id, GlobalResources resourceDetails);
    void deleteResource(UUID id);
    boolean resourceExists(String name);
}
