package com.lms.usermanagementservice.Kafka.Config;

import com.LMS.Kafka.KafkaTopics;
import com.LMS.Kafka.TenantAddedEvent;
import com.lms.tenantcore.TenantContext;
import com.lms.tenantcore.TenantSchemaInitializer;

import com.lms.usermanagementservice.Repository.UserRepository;
import com.lms.usermanagementservice.Seeder.RolesPermissionSeeder;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import javax.sql.DataSource;



@Service
@RequiredArgsConstructor
public class TenantEventListener {

    private static final Logger logger = LoggerFactory.getLogger(TenantEventListener.class);
    private final TenantSchemaInitializer tenantSchemaInitializer;
    private final RolesPermissionSeeder  rolesPermissionSeeder  ;
    private final UserRepository userRepository;
    private final DataSource dataSource;

    @Value("${spring.flyway.locations}")
    private String migrateLocation;

    @KafkaListener(topics = KafkaTopics.ADD_TENANT_TOPIC, groupId = "user-mgmt-group", containerFactory = "kafkaListenerContainerFactory")
    public void handleTenantAddedEvent(TenantAddedEvent event) throws Exception {
        logger.info("Received TenantAddedEvent in User Management: {}", event);

        // TODO: Initialize tenant-specific setup here, e.g., create schema, default admin, etc.
        System.out.println("Received TenantAddedEvent in User Management: " + event);

        String schemaName = event.schemaName();

        tenantSchemaInitializer.createTenantSchema(schemaName);

        // set current Tenant
        TenantContext.setCurrentTenant(schemaName);

        // create table in the schema
        createTableInSchema(schemaName);
        userRepository.count();

        //seed roles and permission
        rolesPermissionSeeder.seedRolePermission(schemaName);




    }

    public void createTableInSchema(String schemaName) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas(schemaName)
                .locations(migrateLocation)
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
    }
}
