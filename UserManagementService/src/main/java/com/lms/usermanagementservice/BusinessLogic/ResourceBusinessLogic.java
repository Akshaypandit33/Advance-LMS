package com.lms.usermanagementservice.BusinessLogic;

import com.LMS.Constants.ResourcesName;
import com.LMS.DTOs.ResourceDTO.ResourceRequestDTO;
import com.LMS.Exceptions.ResourceService.ResourceAlreadyExistsException;
import com.LMS.Exceptions.ResourceService.ResourceNotFoundException;
import com.lms.usermanagementservice.Model.Resource;
import com.lms.usermanagementservice.Repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResourceBusinessLogic {

    private final ResourceRepository resourceRepository;

    // 1. Add Resource
    public Resource addResource(ResourceRequestDTO dto) {
        ResourcesName resourceName = ResourcesName.valueOf(dto.resourceName().trim().toUpperCase());

        if (resourceRepository.existsByResourceName(resourceName)) {
            throw new ResourceAlreadyExistsException("Resource already exists: " + resourceName);
        }

        Resource resource = Resource.builder()
                .resourceName(resourceName)
                .build();
        return resourceRepository.save(resource);
    }

    public List<Resource> findAllResources() {
        return resourceRepository.findAll();
    }

    // 3. Find resource by ID
    public Resource findResourceById(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with ID: " + id));
    }
}
