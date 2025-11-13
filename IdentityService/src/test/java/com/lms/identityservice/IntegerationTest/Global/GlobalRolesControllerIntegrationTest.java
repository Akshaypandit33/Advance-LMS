package com.lms.identityservice.IntegerationTest.Global;

import com.LMS.DTOs.RolesDTO.RoleRequestDTO;
import com.LMS.DTOs.RolesDTO.RoleResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class GlobalRolesControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:17.5");

    private static UUID createdRoleId;
    private static final String ROLE_NAME = "ADMIN";
    private static final String ROLE_DESCRIPTION = "Administrator role";

    @BeforeAll
    public void setup() throws SQLException {
        if (!postgreSQLContainer.isRunning()) {
            postgreSQLContainer.start();
        }

        String script = """
            CREATE SCHEMA IF NOT EXISTS global_metadata;
            
            CREATE TABLE global_metadata.global_roles (
                id UUID PRIMARY KEY,
                name VARCHAR(255) UNIQUE NOT NULL,
                description TEXT,
                is_template BOOLEAN DEFAULT false,
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

    @Test
    @Order(1)
    public void shouldCreateRole() throws Exception {
        RoleRequestDTO request = new RoleRequestDTO(
                ROLE_NAME,
                ROLE_DESCRIPTION,
                false,
                false
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/global-roles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roleId").exists())
                .andExpect(jsonPath("$.roleName").value(ROLE_NAME))
                .andExpect(jsonPath("$.descriptions").value(ROLE_DESCRIPTION))
                .andExpect(jsonPath("$.isTemplate").value(false));

        // Store the created role ID for subsequent tests
        String response = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/global-roles/name/" + ROLE_NAME))
                .andReturn().getResponse().getContentAsString();

        RoleResponseDTO roleResponse = objectMapper.readValue(response, RoleResponseDTO.class);
        createdRoleId = roleResponse.roleId();
    }

    @Test
    @Order(2)
    public void shouldGetRoleById() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/global-roles/" + createdRoleId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleId").value(createdRoleId.toString()))
                .andExpect(jsonPath("$.roleName").value(ROLE_NAME))
                .andExpect(jsonPath("$.descriptions").value(ROLE_DESCRIPTION));
    }

    @Test
    @Order(3)
    public void shouldGetRoleByName() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/global-roles/name/" + ROLE_NAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value(ROLE_NAME))
                .andExpect(jsonPath("$.descriptions").value(ROLE_DESCRIPTION));
    }

    @Test
    @Order(4)
    public void shouldGetAllRoles() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/global-roles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roleId").value(createdRoleId.toString()))
                .andExpect(jsonPath("$[0].roleName").value(ROLE_NAME));
    }

    @Test
    @Order(5)
    public void shouldGetTemplateRoles() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/global-roles/templates")
                                .param("isTemplate", "false"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roleId").value(createdRoleId.toString()))
                .andExpect(jsonPath("$[0].isTemplate").value(false));
    }

    @Test
    @Order(6)
    public void shouldUpdateRole() throws Exception {
        RoleRequestDTO updateRequest = new RoleRequestDTO(
                "UPDATED_" + ROLE_NAME,
                "Updated description",
                false,
                true
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/v1/global-roles/" + createdRoleId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value("UPDATED_" + ROLE_NAME))
                .andExpect(jsonPath("$.descriptions").value("Updated description"))
                .andExpect(jsonPath("$.isTemplate").value(true));
    }

    @Test
    @Order(7)
    public void shouldCloneRoleAsTemplate() throws Exception {
        String newRoleName = "CLONED_" + ROLE_NAME;

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/global-roles/" + createdRoleId + "/clone")
                                .param("newRoleName", newRoleName))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roleName").value(newRoleName))
                .andExpect(jsonPath("$.isTemplate").value(true));
    }

}