package com.lms.identityservice.Service.Global.Impl;

import com.LMS.Exceptions.ResourceService.ResourceAlreadyExistsException;
import com.LMS.Exceptions.ResourceService.ResourceNotFoundException;
import com.lms.identityservice.Model.Global.GlobalResources;
import com.lms.identityservice.Repository.Global.GlobalResourceRepository;
import com.lms.identityservice.Service.Global.GlobalResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class GlobalResourceServiceImpl implements GlobalResourceService {

    private final GlobalResourceRepository globalResourcesRepository;


    @Override
    public GlobalResources createResource(GlobalResources resource) {
        if (resourceExists(resource.getName())) {
            throw new ResourceAlreadyExistsException("Resource with name " + resource.getName() + " already exists");
        }
        return globalResourcesRepository.save(resource);
    }

    @Transactional(readOnly = true)
    @Override
    public GlobalResources getResourceById(UUID id) {
        return globalResourcesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public GlobalResources getResourceByName(String name) {
        return globalResourcesRepository.findByNameIgnoreCase(name.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with name: " + name));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GlobalResources> getAllResources() {
        return globalResourcesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GlobalResources> getResourcesByType(String resourceType) {
        return globalResourcesRepository.findGlobalResourcesByResourceTypeIgnoreCase(resourceType.toUpperCase());
    }

    @Override
    public GlobalResources updateResource(UUID id, GlobalResources resourceDetails) {
        GlobalResources resource = getResourceById(id);
        if(resourceDetails.getName() != null) {
            resource.setName(resourceDetails.getName());
        }
       if(resourceDetails.getResourceType() != null) {
           resource.setResourceType(resourceDetails.getResourceType());
       }
        if(resourceDetails.getDescription() != null) {
            resource.setDescription(resourceDetails.getDescription());
        }
        return globalResourcesRepository.save(resource);
    }

    @Override
    public void deleteResource(UUID id) {
        GlobalResources resource = getResourceById(id);
        globalResourcesRepository.delete(resource);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean resourceExists(String name) {
        return globalResourcesRepository.existsByName(name.toUpperCase());
    }
}
