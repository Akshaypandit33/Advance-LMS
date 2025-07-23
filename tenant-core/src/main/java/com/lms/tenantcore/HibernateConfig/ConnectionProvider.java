package com.lms.tenantcore.HibernateConfig;

import com.LMS.Exceptions.TenantService.TenantNotFoundExceptions;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ConnectionProvider implements MultiTenantConnectionProvider, HibernatePropertiesCustomizer {

    private final DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(ConnectionProvider.class);

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection() ;
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {

    }

    @Override
    public Connection getConnection(Object tenantIdentifier) throws SQLException {
        String tenant = (String) tenantIdentifier;
        Connection connection = dataSource.getConnection();

        if(!schemaExists(connection, tenant))
        {
            connection.close();
            throw new TenantNotFoundExceptions("Schema for tenant "+tenant+" not exists");
        }
        connection.setSchema(tenant);
        logger.info(" Switched to schema: " + tenant);
        return connection;
    }

    @Override
    public void releaseConnection(Object tenantIdentifier, Connection connection) throws SQLException {
        connection.setSchema("public");
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override

    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap( Class<T> unwrapType) {
        return null;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }

    private boolean schemaExists(Connection connection, String schemaName) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT schema_name FROM information_schema.schemata WHERE schema_name = ?")) {
            stmt.setString(1, schemaName);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true if schema exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
