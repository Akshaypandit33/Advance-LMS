package com.lms.usermanagementservice.BusinessLogic;

import com.LMS.Constants.ResourcesName;
import com.LMS.DTOs.DeleteResourceDTO;
import com.LMS.DTOs.ResourceDTO.ResourceRequestDTO;
import com.LMS.Exceptions.ResourceService.ResourceAlreadyExistsException;
import com.LMS.Exceptions.ResourceService.ResourceNotFoundException;
import com.lms.usermanagementservice.Model.Globals.Global_Resources;

import com.lms.usermanagementservice.Repository.Global.Global_ResourceRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Global_ResourceBusinessLogic {

    private final Global_ResourceRepository resourceRepository;

    // 1. Add Resource
    @Transactional
    public Global_Resources addResource(ResourceRequestDTO dto) {
        ResourcesName resourceName = ResourcesName.valueOf(dto.resourceName().trim().toUpperCase());

        if (resourceRepository.existsByResourceName(resourceName)) {
            throw new ResourceAlreadyExistsException("Resource already exists: " + resourceName);
        }

        Global_Resources resource = Global_Resources.builder()
                .resourceName(resourceName)
                .build();
        return resourceRepository.save(resource);
    }

    public Page<Global_Resources> findAllResources(Pageable  pageable) {
        return resourceRepository.findAll(pageable);
    }

    // 3. Find resource by ID
    public Global_Resources findResourceById(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with ID: " + id));
    }

    @Transactional
    public DeleteResourceDTO deleteResourceById(Long id) {
        Optional<Global_Resources> resource = resourceRepository.findById(id);
        DeleteResourceDTO deleteResourceDTO = null;
        if (resource.isPresent()) {
            resourceRepository.delete(resource.get());
            deleteResourceDTO = new DeleteResourceDTO("SUCCESS","Resource removed successfully");
        }else {
            deleteResourceDTO = new DeleteResourceDTO("FAILURE","Resource not found with ID: " + id);
        }
       return deleteResourceDTO;
    }
}
