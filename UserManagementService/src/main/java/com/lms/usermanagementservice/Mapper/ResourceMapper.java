package com.lms.usermanagementservice.Mapper;

import com.LMS.Constants.ResourcesName;
import com.LMS.DTOs.ResourceDTO.ResourceRequestDTO;
import com.LMS.DTOs.ResourceDTO.ResourceResponseDTO;
import com.lms.usermanagementservice.Model.Resource;

public class ResourceMapper {

    public static Resource toModel(ResourceRequestDTO resourceRequestDTO) {
        return Resource.builder()
                .resourceName(ResourcesName.valueOf(resourceRequestDTO.resourceName().toUpperCase()))
                .build();

    }

    public static ResourceResponseDTO toResponseDTO(Resource resource) {
        return new ResourceResponseDTO(
                resource.getId(),
                resource.getResorceNameString()
        );
    }
}
