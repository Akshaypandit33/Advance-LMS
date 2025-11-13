package com.lms.identityservice.IntegerationTest.Global;

import com.LMS.DTOs.ResourceDTO.ResourceRequestDTO;
import com.LMS.DTOs.ResourceDTO.ResourceResponseDTO;
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

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
@AutoConfigureMockMvc
public class GlobalResourceControllerIntegrationTest {

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

    private static final String RESOURCE_NAME = "TEST_RESOURCE";
    private static final String RESOURCE_TYPE = "TEST_TYPE";
    private static final String RESOURCE_DESCRIPTION = "Test Description";
    private static UUID createdResourceId;

    static final String SchemaName ="global_metadata";

    @BeforeAll
    public void setup() throws SQLException {
        if (!postgreSQLContainer.isRunning()) {
            postgreSQLContainer.start();
        }

        String script = """
            CREATE SCHEMA IF NOT EXISTS global_metadata;
                CREATE TABLE global_metadata.global_resources (
                                                                id UUID PRIMARY KEY,
                                                                name VARCHAR(255) UNIQUE NOT NULL,
                                                                description TEXT,
                                                                resource_type VARCHAR(255),
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
    public void shouldCreateResource() throws Exception {
       ResourceRequestDTO request = new ResourceRequestDTO(
                RESOURCE_NAME,
                RESOURCE_DESCRIPTION,
                RESOURCE_TYPE
        );

         mockMvc.perform(
                MockMvcRequestBuilders.
                post("/api/v1/global-resources")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resourceId").exists())
                .andExpect(jsonPath("$.resourceType").value(request.resourceType().toUpperCase()))
                .andExpect(jsonPath("$.resourceName").value(request.resourceName().toUpperCase()))
                .andExpect(jsonPath("$.descriptions").exists());

        String response = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/global-resources/name/{name}", RESOURCE_NAME))
                .andReturn().getResponse().getContentAsString();

        createdResourceId = objectMapper.readValue(response, ResourceResponseDTO.class).resourceId();

    }

    @Test
    @Order(2)
    public void shouldGetResourceById() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/global-resources/{id}", createdResourceId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resourceId").value(createdResourceId.toString()))
                .andExpect(jsonPath("$.resourceName").value(RESOURCE_NAME))
                .andExpect(jsonPath("$.resourceType").value(RESOURCE_TYPE))
                .andExpect(jsonPath("$.descriptions").exists());
    }

    @Test
    @Order(3)
    public void shouldGetResourceByName() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/global-resources/name/{name}", RESOURCE_NAME)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resourceName").value(RESOURCE_NAME))
                .andExpect(jsonPath("$.resourceType").value(RESOURCE_TYPE))
                .andExpect(jsonPath("$.descriptions").exists());
    }

    @Test
    @Order(4)
    public void shouldGetAllResources() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/global-resources")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].resourceName").value(RESOURCE_NAME))
                .andExpect(jsonPath("$[0].resourceType").value(RESOURCE_TYPE))
                .andExpect(jsonPath("$[0].descriptions").exists());
    }

    @Test
    @Order(5)
    public void shouldGetResourcesByType() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/global-resources/type/{resourceType}", RESOURCE_TYPE)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].resourceName").value(RESOURCE_NAME))
                .andExpect(jsonPath("$[0].resourceType").value(RESOURCE_TYPE))
                .andExpect(jsonPath("$[0].descriptions").exists());
    }

    @Test
    @Order(6)
    public void shouldUpdateResource() throws Exception {
        ResourceRequestDTO updateRequest = new ResourceRequestDTO(
                "UPDATED_RESOURCE",
                "Updated Description",
                "UPDATED_TYPE"
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/v1/global-resources/{id}", createdResourceId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resourceName").value("UPDATED_RESOURCE"))
                .andExpect(jsonPath("$.descriptions").value("Updated Description"))
                .andExpect(jsonPath("$.resourceType").value("UPDATED_TYPE"));
    }


}
