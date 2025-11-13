package com.lms.identityservice.Config;


import com.LMS.Constants.GlobalConstant;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

// ... imports ...

@Configuration
public class SwaggerConfig {

    @Bean
    public OperationCustomizer customOperationCustomizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {

            final String tenantHeaderName = GlobalConstant.HEADER_TENANT_ID;

            // Use the raw string "header" instead of Parameter.In.HEADER
            final String headerIn = "header";

            // Check if the header is already documented
            boolean tenantIdExists = operation.getParameters() != null && operation.getParameters().stream()
                    // Replaced Parameter.In.HEADER with "header"
                    .anyMatch(p -> tenantHeaderName.equals(p.getName()) && headerIn.equals(p.getIn()));

            if (!tenantIdExists) {

                HeaderParameter tenantIdHeader = (HeaderParameter) new HeaderParameter()
                        .name(tenantHeaderName)
                        // Replaced Parameter.In.HEADER with "header"
                        .in(headerIn)
                        .description("Required ID of the tenant for multi-tenancy context.")
                        .required(true);

                operation.addParametersItem(tenantIdHeader);
            }

            return operation;
        };
    }
}