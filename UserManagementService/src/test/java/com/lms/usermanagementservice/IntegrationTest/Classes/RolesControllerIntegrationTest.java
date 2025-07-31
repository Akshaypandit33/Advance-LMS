package com.lms.usermanagementservice.IntegrationTest.Classes;

import com.LMS.Constants.GlobalConstant;
import com.LMS.DTOs.RolesDTO.RoleRequestDTO;
import com.LMS.DTOs.RolesDTO.RoleResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.tenantcore.TenantContext;
import com.lms.tenantcore.TenantSchemaInitializer;
import com.lms.usermanagementservice.IntegrationTest.IntegrationTestUtil;
import com.lms.usermanagementservice.Kafka.Config.TenantEventListener;
import com.lms.usermanagementservice.Seeder.RolesPermissionSeeder;
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


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RolesControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private RolesPermissionSeeder rolesPermissionSeeder ;
    @Autowired
    private TenantSchemaInitializer tenantSchemaInitializer ;
    @Autowired
    private TenantEventListener tenantEventListener;
    @Autowired
    private ObjectMapper objectMapper;
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(PostgreSQLContainer.IMAGE + ":17.5");

    static final String SchemaName ="niet_00";

    @BeforeAll
    public void seedDatabase() throws Exception {
        if (!postgreSQLContainer.isRunning()){
            postgreSQLContainer.start();
        }
        IntegrationTestUtil.seedTenantData(SchemaName,tenantSchemaInitializer,tenantEventListener,rolesPermissionSeeder);
    }

    @Test
    @Order(1)
    void addRoles() throws Exception {
        RoleRequestDTO requestDTO = new RoleRequestDTO("TEST_ADMIN","TEST_ADMIN ROLE DESCRIPTION");
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/v1/roles")
                        .header(GlobalConstant.HEADER_TENANT_ID,SchemaName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roleId").exists())
                .andExpect(jsonPath("$.roleName").value(requestDTO.roleName().toUpperCase()))
                .andExpect(jsonPath("$.descriptions").value(requestDTO.descriptions()))
        ;
    }

    @Test
    @Order(2)
    void getRolesByName() throws Exception {
        String roleNAme = "TEST_ADMIN";
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/v1/roles/search?name="+roleNAme)
                        .header(GlobalConstant.HEADER_TENANT_ID,SchemaName)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleId").exists())
                .andExpect(jsonPath("$.roleName").value(roleNAme))
                .andExpect(jsonPath("$.descriptions").exists());
    }

    @Test
    @Order(3)
    void deleteRolesById() throws Exception {
        String roleName = "TEST_ADMIN";

        String jsonResponse = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/v1/roles/search?name=" + roleName)
                                .header(GlobalConstant.HEADER_TENANT_ID, SchemaName)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Assuming search returns a list of roles
       RoleResponseDTO roles = objectMapper.readValue(
                jsonResponse, RoleResponseDTO.class);


        String roleId = String.valueOf(roles.roleId()); // get the first matching role ID
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/v1/roles/" + roleId)
                                .header(GlobalConstant.HEADER_TENANT_ID, SchemaName)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.roleName").exists());
    }

}
