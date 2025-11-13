package com.lms.identityservice.IntegerationTest.Global;


import com.LMS.DTOs.ActionsDTO.ActionRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GlobalActionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TenantSchemaInitializer tenantSchemaInitializer ;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(PostgreSQLContainer.IMAGE + ":17.5");

    static final String SchemaName ="global_metadata";

    @BeforeAll
    public void setup() throws SQLException {
        if (!postgreSQLContainer.isRunning()) {
            postgreSQLContainer.start();
        }

        String script = """
            CREATE SCHEMA IF NOT EXISTS global_metadata;
            
            CREATE TABLE IF NOT EXISTS global_metadata.global_actions (
                id UUID PRIMARY KEY,
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
    }

    @Order(1)
    @Test
    public void addAction() throws Exception {
        ActionRequestDTO requestDTO = new ActionRequestDTO("create","creating actions");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/global/actions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.actionId").exists())
                .andExpect(jsonPath("$.actionName").value(requestDTO.actionName().toUpperCase()));
    }


    @Test
    @Order(2)
    public void getActionById() throws Exception {
        // First, create the action
        ActionRequestDTO requestDTO = new ActionRequestDTO("read", "read actions");
        String response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/global/actions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
        ).andReturn().getResponse().getContentAsString();

        UUID actionId = UUID.fromString(objectMapper.readTree(response).get("actionId").asText());

        // Now test GET by ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/global/actions/{id}", actionId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actionId").value(actionId.toString()))
                .andExpect(jsonPath("$.actionName").value(requestDTO.actionName().toUpperCase()));
    }

    @Test
    @Order(3)
    public void getActionByName() throws Exception {
        String name = "read";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/global/actions/name/{name}", name))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actionName").value(name.toUpperCase()));
    }

    @Test
    @Order(4)
    public void getAllActions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/global/actions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(org.hamcrest.Matchers.greaterThan(0)));
    }

    @Test
    @Order(5)
    public void updateAction() throws Exception {
        // First, create an action
        ActionRequestDTO requestDTO = new ActionRequestDTO("update", "update actions");
        String response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/global/actions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
        ).andReturn().getResponse().getContentAsString();

        UUID actionId = UUID.fromString(objectMapper.readTree(response).get("actionId").asText());

        // Update the action
        ActionRequestDTO updateDTO = new ActionRequestDTO("updateModified", "modified description");
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/v1/global/actions/{id}", actionId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDTO))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actionName").value(updateDTO.actionName().toUpperCase()));
    }

    @Test
    @Order(6)
    public void deleteAction() throws Exception {
        // First, create an action
        ActionRequestDTO requestDTO = new ActionRequestDTO("delete", "delete actions");
        String response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/global/actions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
        ).andReturn().getResponse().getContentAsString();

        UUID actionId = UUID.fromString(objectMapper.readTree(response).get("actionId").asText());

        // Delete the action
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/global/actions/{id}", actionId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DELETED"));
    }


}
