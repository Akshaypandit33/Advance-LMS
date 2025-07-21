package com.lms.usermanagementservice.Controller;

import com.LMS.Constants.GlobalConstant;
import com.LMS.DTOs.UserService.UserRequestDTO;
import com.LMS.DTOs.UserService.UserResponseDTO;
import com.lms.tenantcore.TenantContext;
import com.lms.usermanagementservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserServiceController {

    private final UserService userService;
    private final String TenantIdHeader = GlobalConstant.HEADER_TENANT_ID;
    @PostMapping("/createUser")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO

        ) {
        return ResponseEntity.ok(userService.createUser(userRequestDTO));
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping()
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers(PageRequest.of(0, 10)));
    }

    @GetMapping("/getHeaders")
    String getHeaders()
    {
        return TenantContext.getCurrentTenant();
    }

}
