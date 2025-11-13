package com.lms.usermanagementservice.Gloabal_metadata.Seeder;

import com.LMS.Constants.GlobalConstant;
import com.lms.tenantcore.TenantContext;
import com.lms.usermanagementservice.Repository.Global.Global_ActionsRepository;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@RequiredArgsConstructor
@Component
public class FlywaySeeder implements CommandLineRunner {
    @Value("${spring.flyway.locations}")
    private String migrateLocation;
    private final Global_ActionsRepository globalActionsRepository;
    private final DataSource dataSource;
    private final GlobalRolesSeeder  globalRolesSeeder  ;
    @Override
    public void run(String... args) throws Exception {
        TenantContext.setCurrentTenant(GlobalConstant.GLOBAL_METADATA);
        createTableInSchema();
        globalActionsRepository.count();
        globalRolesSeeder.seedData();
    }

    public void createTableInSchema() {

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas(GlobalConstant.GLOBAL_METADATA)
                .locations(migrateLocation+"/global_metadata")
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
    }
}
