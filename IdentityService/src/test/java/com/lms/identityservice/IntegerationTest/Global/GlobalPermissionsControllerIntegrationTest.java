package com.lms.identityservice.IntegerationTest.Global;

import com.LMS.DTOs.PermissionDTO.PermissionRequestDTO;
import com.LMS.DTOs.PermissionDTO.PermissionResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.identityservice.Model.Global.GlobalActions;
import com.lms.identityservice.Model.Global.GlobalResources;
import com.lms.tenantcore.TenantSchemaInitializer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
@AutoConfigureMockMvc
public class GlobalPermissionsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TenantSchemaInitializer tenantSchemaInitializer;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(PostgreSQLContainer.IMAGE + ":17.5");

    private static UUID actionId;
    private static UUID resourceId;
    private static UUID permissionId;
    private static final String ACTION_NAME = "CREATE";
    private static final String RESOURCE_NAME = "COURSE";
    private static final String PERMISSION_NAME = ACTION_NAME + "_" + RESOURCE_NAME;

    @BeforeAll
    public void setup() throws SQLException {
        if (!postgreSQLContainer.isRunning()) {
            postgreSQLContainer.start();
        }

        String script = """
            CREATE SCHEMA IF NOT EXISTS global_metadata;
            
            CREATE TABLE global_metadata.global_actions (
                id UUID PRIMARY KEY,
                name VARCHAR(255) UNIQUE NOT NULL,
                description TEXT,
                created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
            );
            
            CREATE TABLE global_metadata.global_resources (
                id UUID PRIMARY KEY,
                name VARCHAR(255) UNIQUE NOT NULL,
                description TEXT,
                resource_type VARCHAR(255),
                created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
            );
            
            CREATE TABLE global_metadata.global_permissions (
                id UUID PRIMARY KEY,
                action_id UUID NOT NULL REFERENCES global_metadata.global_actions(id),
                resource_id UUID NOT NULL REFERENCES global_metadata.global_resources(id),
                name VARCHAR(255) UNIQUE NOT NULL,
                description TEXT,
                created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
            );
            """;


        try (Connection conn = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword());
             Statement stmt = conn.createStatement()) {
            stmt.execute(script);
        }

        // Create test action and resource
        actionId = createTestAction(ACTION_NAME);
        resourceId = createTestResource(RESOURCE_NAME);
    }

    private UUID createTestAction(String name) throws SQLException {
        UUID id = UUID.randomUUID();
        String sql = String.format(
                "INSERT INTO global_metadata.global_actions (id, name, description) " +
                        "VALUES ('%s', '%s', 'Test action')",
                id, name);

        executeSql(sql);
        return id;
    }

    private UUID createTestResource(String name) throws SQLException {
        UUID id = UUID.randomUUID();
        String sql = String.format(
                "INSERT INTO global_metadata.global_resources (id, name, description, resource_type) " +
                        "VALUES ('%s', '%s', 'Test resource', 'COURSE')",
                id, name);

        executeSql(sql);
        return id;
    }
    private void executeSql(String sql) throws SQLException {
        try (Connection conn = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword());
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Test
    @Order(1)
    public void shouldCreatePermission() throws Exception {
        PermissionRequestDTO request = new PermissionRequestDTO(actionId, resourceId);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/permissions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.permissionId").exists())
                .andExpect(jsonPath("$.name").value(PERMISSION_NAME))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.actions.actionId").value(actionId.toString()))
                .andExpect(jsonPath("$.resources.resourceId").value(resourceId.toString()));

        // Store the created permission ID for subsequent tests
        String response = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/permissions/action/" + actionId))
                .andReturn().getResponse().getContentAsString();

        PermissionResponseDTO[] permissions = objectMapper.readValue(response, PermissionResponseDTO[].class);
        permissionId = permissions[0].permissionId();
    }

    @Test
    @Order(2)
    public void shouldGetPermissionById() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/permissions/" + permissionId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.permissionId").value(permissionId.toString()))
                .andExpect(jsonPath("$.name").value(PERMISSION_NAME))
                .andExpect(jsonPath("$.actions.actionId").value(actionId.toString()))
                .andExpect(jsonPath("$.resources.resourceId").value(resourceId.toString()));
    }

    @Test
    @Order(3)
    public void shouldGetPermissionByName() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/permissions/name/" + PERMISSION_NAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(PERMISSION_NAME))
                .andExpect(jsonPath("$.actions.actionId").value(actionId.toString()))
                .andExpect(jsonPath("$.resources.resourceId").value(resourceId.toString()));
    }

    @Test
    @Order(4)
    public void shouldGetAllPermissions() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/permissions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].permissionId").value(permissionId.toString()))
                .andExpect(jsonPath("$[0].name").value(PERMISSION_NAME));
    }

    @Test
    @Order(5)
    public void shouldGetPermissionsByAction() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/permissions/action/" + actionId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].permissionId").value(permissionId.toString()))
                .andExpect(jsonPath("$[0].actions.actionId").value(actionId.toString()));
    }

    @Test
    @Order(6)
    public void shouldGetPermissionsByResource() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/permissions/resource/" + resourceId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].permissionId").value(permissionId.toString()))
                .andExpect(jsonPath("$[0].resources.resourceId").value(resourceId.toString()));
    }

}

