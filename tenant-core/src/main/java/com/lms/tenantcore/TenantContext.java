package com.lms.tenantcore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantContext {

    private static final Logger logger = LoggerFactory.getLogger(TenantContext.class);

    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static void setCurrentTenant(String value) {
        logger.info("Setting Current tenant is {}", value);
        currentTenant.set(value);
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void removeCurrentTenant() {
        currentTenant.remove();
    }
}