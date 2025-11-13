package com.lms.usermanagementservice.Service;

import com.LMS.DTOs.DeleteResourceDTO;
import com.LMS.DTOs.ResourceDTO.ResourceRequestDTO;
import com.LMS.DTOs.ResourceDTO.ResourceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResourceService {
    ResourceResponseDTO addResource(ResourceRequestDTO resourceRequestDTO);
    Page<ResourceResponseDTO> findAllResources(Pageable pageable);
    ResourceResponseDTO findResourceById(Long id);
    DeleteResourceDTO deleteResourceById(Long id);
}
