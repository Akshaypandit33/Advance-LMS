package com.lms.tenantcore.Resolver;

@FunctionalInterface
public interface TenantResolver<T> {
    String resolveTenantId(T tenant);
}
