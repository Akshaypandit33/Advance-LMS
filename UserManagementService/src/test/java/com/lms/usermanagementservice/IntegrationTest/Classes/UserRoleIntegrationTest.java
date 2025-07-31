package com.lms.usermanagementservice.IntegrationTest.Classes;

import com.LMS.Constants.GlobalConstant;
import com.LMS.DTOs.UserService.UserRole.UserRoleRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRoleIntegrationTest {

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

    @Order(1)
    @Test
    public void test_AssignUserRole() throws Exception {
        IntegrationTestUtil.CreatedUserInfo userInfo = IntegrationTestUtil.createTestUser(mockMvc,objectMapper,SchemaName);
        UserRoleRequestDTO request = new UserRoleRequestDTO(UUID.fromString(userInfo.userId()),"student");

        mockMvc.perform(
                MockMvcRequestBuilders
                .post("/v1/user-roles")
                .contentType(MediaType.APPLICATION_JSON)
                        .header(GlobalConstant.HEADER_TENANT_ID,SchemaName)
                .content(objectMapper.writeValueAsString(request))
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userRoleId").exists())
                .andExpect(jsonPath("$.userId").value(userInfo.userId()))
                .andExpect(jsonPath(("$.fullName")).value(userInfo.requestDTO().firstName()+" "+ userInfo.requestDTO().lastName()))
                .andExpect(jsonPath("$.roleName").value("student".toUpperCase()))
                .andExpect(jsonPath("$.roleId").exists());
    }

}
