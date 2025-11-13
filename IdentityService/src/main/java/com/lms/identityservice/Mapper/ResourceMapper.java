package com.lms.identityservice.Mapper;

import com.LMS.DTOs.ResourceDTO.ResourceRequestDTO;
import com.LMS.DTOs.ResourceDTO.ResourceResponseDTO;
import com.lms.identityservice.Model.Global.GlobalResources;

public class ResourceMapper {

    public static ResourceResponseDTO toeResourceResponseDTO(GlobalResources resource){
        return new ResourceResponseDTO(
                resource.getId(),
                resource.getName(),
                resource.getDescription(),
                resource.getResourceType()
        );
    }

    public static GlobalResources toGlobalResource(ResourceRequestDTO resource){
        return GlobalResources.builder()
                .name(resource.resourceName())
                .description(resource.description())
                .resourceType(resource.resourceType())
                .build();
    }
}
