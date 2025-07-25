package com.lms.tenantcore.Resolver;


import com.LMS.Constants.GlobalConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class HttpHeaderTenantResolver implements TenantResolver<HttpServletRequest>{
    @Override
    public String resolveTenantId(HttpServletRequest request) {
        return request.getHeader(GlobalConstant.HEADER_TENANT_ID);
    }
}
