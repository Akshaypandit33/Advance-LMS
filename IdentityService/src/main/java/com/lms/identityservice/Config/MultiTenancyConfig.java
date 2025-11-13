package com.lms.identityservice.Config;

import com.lms.tenantcore.Config.AbstractMultiTenantJpaConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.lms.identityservice.Repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class MultiTenancyConfig extends AbstractMultiTenantJpaConfig {
    @Override
    protected String[] getEntityPackages() {
        return new String[]{"com.lms.identityservice.Model"};
    }

    @Override
    protected String getPersistenceUnitName() {
        return "IdentityService";
    }
}
