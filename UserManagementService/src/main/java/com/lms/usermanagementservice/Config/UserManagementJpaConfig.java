package com.lms.usermanagementservice.Config;

import com.lms.tenantcore.Config.AbstractMultiTenantJpaConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.lms.usermanagementservice.Repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class UserManagementJpaConfig extends AbstractMultiTenantJpaConfig {

    @Override
    protected String[] getEntityPackages() {
        return new String[] { "com.lms.usermanagementservice.Model" };
    }

    @Override
    protected String getPersistenceUnitName() {
        return "user-management";
    }
}
