package com.LMS.DTOs.ResourceDTO;

import java.util.UUID;

public record ResourceResponseDTO(
        UUID resourceId,
        String resourceName,
        String descriptions,
        String resourceType
) {
}
