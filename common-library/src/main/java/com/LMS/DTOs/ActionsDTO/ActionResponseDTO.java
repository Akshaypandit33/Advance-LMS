package com.LMS.DTOs.ActionsDTO;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ActionResponseDTO(
        UUID actionId,
        String actionName,
        String descriptions,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt

) {
}
