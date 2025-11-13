package com.lms.tenantcore;


import com.LMS.Constants.GlobalConstant;
import com.lms.tenantcore.Resolver.HttpHeaderTenantResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class TenantInterceptor implements HandlerInterceptor {

    private final HttpHeaderTenantResolver tenantResolver;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenant =tenantResolver.resolveTenantId(request);
        if(tenant == null){
            if(request.getRequestURI().startsWith("/v1/actions"))
            {
                TenantContext.setCurrentTenant(GlobalConstant.GLOBAL_METADATA);
            }
            else {
                TenantContext.setCurrentTenant("public");
            }
        }
        else {
            TenantContext.setCurrentTenant(tenant);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.removeCurrentTenant();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TenantContext.removeCurrentTenant();
    }
}
