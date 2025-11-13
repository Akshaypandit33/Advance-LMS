package com.LMS.DTOs.ResourceDTO;

public record ResourceRequestDTO(
        String resourceName,
        String description,
        String resourceType
) {
}
