package com.lms.tenantcore;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
@RequiredArgsConstructor
public class TenantSchemaInitializer {

    private final DataSource dataSource;

    public void createTenantSchema(String schemaName) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("CREATE SCHEMA IF NOT EXISTS \"" + schemaName + "\"");

            // Optional: Set search_path if you need to run further queries here
            statement.execute("SET search_path TO \"" + schemaName + "\"");

        } catch (Exception e) {
            throw new RuntimeException("Failed to create schema: " + schemaName, e);
        }
    }
}
