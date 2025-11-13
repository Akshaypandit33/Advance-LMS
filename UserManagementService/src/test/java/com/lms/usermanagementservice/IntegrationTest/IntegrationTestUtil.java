package com.lms.usermanagementservice.IntegrationTest;

import com.LMS.DTOs.UserService.UserRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.tenantcore.TenantContext;
import com.lms.tenantcore.TenantSchemaInitializer;
import com.lms.usermanagementservice.Kafka.Config.TenantEventListener;


import java.util.Collections;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class IntegrationTestUtil {

    public record CreatedUserInfo(String userId, UserRequestDTO requestDTO) {}

    public static void seedTenantData(
            String schema,
            TenantSchemaInitializer initializer,
            TenantEventListener listener,
            RolesPermissionSeeder seeder
    ) throws Exception {
        initializer.createTenantSchema(schema);
        listener.createTableInSchema(schema);
        TenantContext.setCurrentTenant(schema);
        seeder.seedRolePermission(schema);
    }

    public static CreatedUserInfo createTestUser(MockMvc mockMvc, ObjectMapper objectMapper, String tenantId) throws Exception {
        UserRequestDTO requestDTO = new UserRequestDTO(
                "Akshay",
                "Pandit",
                "akshaypandit33@gmail.com",
                "Password123",
                "9876598649",
                "Male",
                "Active",
                Collections.singletonList("Super_admin")
        );

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/users")
                        .header("X-Tenant-ID", tenantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andReturn();

        String userId = objectMapper.readTree(result.getResponse().getContentAsString()).get("userId").asText();
        return new CreatedUserInfo(userId, requestDTO);
    }

}


