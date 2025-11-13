package com.LMS.Constants;

import java.util.UUID;

public class GlobalConstant {
    public static final UUID GLOBAL_TENANT_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final String HEADER_TENANT_ID = "X-Tenant-Id";
    public static final String HEADER_USER_ID = "X-User-Id";
    public static final String DEFAULT_TENANT = "public";

    public static final String SUPER_ADMIN_EMAIL = "superAdmin@lms.com";
    public static final String SUPER_ADMIN_PASSWORD = "Super@123";

    public static final String GLOBAL_METADATA = "global_metadata";
}
