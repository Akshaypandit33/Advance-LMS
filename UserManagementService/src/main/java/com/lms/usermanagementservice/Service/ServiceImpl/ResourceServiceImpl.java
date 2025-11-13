package com.lms.usermanagementservice.Service.ServiceImpl;

import com.LMS.DTOs.DeleteResourceDTO;
import com.LMS.DTOs.ResourceDTO.ResourceRequestDTO;
import com.LMS.DTOs.ResourceDTO.ResourceResponseDTO;
import com.lms.usermanagementservice.BusinessLogic.Global_ResourceBusinessLogic;
import com.lms.usermanagementservice.Mapper.ResourceMapper;
import com.lms.usermanagementservice.Model.Globals.Global_Resources;
import com.lms.usermanagementservice.Service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final Global_ResourceBusinessLogic resourceBusinessLogic;

    @Override
    public ResourceResponseDTO addResource(ResourceRequestDTO resourceRequestDTO) {
        return ResourceMapper.toResponseDTO(resourceBusinessLogic.addResource(resourceRequestDTO));
    }

    @Override
    public Page<ResourceResponseDTO> findAllResources(Pageable pageable) {
        Page<Global_Resources> resources = resourceBusinessLogic.findAllResources(pageable);
        return resources.map(ResourceMapper::toResponseDTO);
    }

    @Override
    public ResourceResponseDTO findResourceById(Long id) {
        return ResourceMapper.toResponseDTO(resourceBusinessLogic.findResourceById(id));
    }

    @Override
    public DeleteResourceDTO deleteResourceById(Long id) {
        return resourceBusinessLogic.deleteResourceById(id);
    }
}
