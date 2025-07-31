package com.lms.usermanagementservice.IntegrationTest.Classes;

import com.LMS.Constants.GlobalConstant;
import com.LMS.DTOs.UserService.UserRequestDTO;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private RolesPermissionSeeder rolesPermissionSeeder ;
    @Autowired
    private TenantSchemaInitializer tenantSchemaInitializer ;
    @Autowired
    private TenantEventListener  tenantEventListener;
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
    void createUserAndFetchDetailsTest() throws Exception {
        // Step 1: Create a test user
        IntegrationTestUtil.CreatedUserInfo createdUser = IntegrationTestUtil.createTestUser(mockMvc, objectMapper, SchemaName);

        // Step 2: Parse created user ID from response
        String userId = createdUser.userId();

        // Optional: Store or log the userId if needed for debugging
        System.out.println("Created user ID: " + userId);

        // Step 3: Fetch the user using GET /v1/users/{id} and verify
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/users/" + userId)
                        .header(GlobalConstant.HEADER_TENANT_ID, SchemaName)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.email").value(createdUser.requestDTO().email()))
                .andExpect(jsonPath("$.firstName").value(createdUser.requestDTO().firstName()))
                .andExpect(jsonPath("$.lastName").value(createdUser.requestDTO().lastName()));
    }


    @Order(2)
    @Test
    void fetchUserByEmail_SuccessTest() throws Exception {
        String email = "akshaypandit33@gmail.com";
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/users/by-email/"+email)
                .header(GlobalConstant.HEADER_TENANT_ID, SchemaName)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.firstName").value("Akshay"));

    }
}
