package com.lms.tenantcore.Resolver;


import com.LMS.Constants.GlobalConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class HttpHeaderTenantResolver implements TenantResolver<HttpServletRequest>{
    @Override
    public String resolveTenantId(HttpServletRequest request) {
        String tenantId = request.getHeader(GlobalConstant.HEADER_TENANT_ID);

        if (tenantId == null || tenantId.isBlank()) {
            String path = request.getRequestURI();
            // Allow Swagger access without tenant header
            if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
                return null; // or "public", or skip resolving
            }

            throw new IllegalStateException("Missing tenant ID in header");
        }

        return tenantId.toLowerCase();
    }
}
