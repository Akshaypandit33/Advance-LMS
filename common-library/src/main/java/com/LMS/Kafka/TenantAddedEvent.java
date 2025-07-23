package com.LMS.Kafka;

import java.util.UUID;

public record TenantAddedEvent(
        String tenantId,
        String name,
        String collegeCode,
        String schemaName
) {
}
