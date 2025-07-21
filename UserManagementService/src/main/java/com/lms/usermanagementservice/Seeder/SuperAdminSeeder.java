package com.lms.usermanagementservice.Seeder;

import com.LMS.Constants.AccountStatus;
import com.LMS.Constants.DefaultRoles;
import com.LMS.Constants.GlobalConstant;
import com.LMS.Exceptions.RoleService.RoleNotFoundException;
import com.lms.usermanagementservice.Model.Roles;
import com.lms.usermanagementservice.Model.UserRole;
import com.lms.usermanagementservice.Model.Users;
import com.lms.usermanagementservice.Repository.RolesRepository;
import com.lms.usermanagementservice.Repository.UserRepository;
import com.lms.usermanagementservice.Repository.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(2)
@RequiredArgsConstructor
public class SuperAdminSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RolesRepository roleRepository;
    private final UserRolesRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(SuperAdminSeeder.class);

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        // Check if SuperAdmin user already exists
        if (userRepository.findByEmail(GlobalConstant.SUPER_ADMIN_EMAIL.toLowerCase()).isPresent()) {
            log.info("SuperAdmin user already exists. Skipping seeding process.");
            return;
        }

        log.info("SuperAdmin user not found. Starting seeding process...");

        // Create SuperAdmin user
        Users superAdminUser = Users.builder()
                .firstName("Akshay")
                .lastName("Kumar")
                .password(passwordEncoder.encode(GlobalConstant.SUPER_ADMIN_PASSWORD))
                .email(GlobalConstant.SUPER_ADMIN_EMAIL)
                .accountStatus(AccountStatus.ACTIVE)
                .gender("Male")
                .phoneNumber("123456789")
                .tenantId(GlobalConstant.GLOBAL_TENANT_ID)
                .build();

        superAdminUser = userRepository.save(superAdminUser);
        log.info("SuperAdmin user created successfully with email: {}", superAdminUser.getEmail());

        // Get SuperAdmin role
        Roles superAdminRole = roleRepository.findByRoleName(DefaultRoles.SUPER_ADMIN.name())
                .orElseThrow(() -> new RoleNotFoundException(DefaultRoles.SUPER_ADMIN.name() + " role not found"));

        // Create user-role mapping
        UserRole userRole = UserRole.builder()
                .user(superAdminUser)
                .role(superAdminRole)
                .tenantId(GlobalConstant.GLOBAL_TENANT_ID)
                .build();

        userRoleRepository.save(userRole);
        log.info("SuperAdmin user-role mapping created successfully.");

        log.info("SuperAdmin seeding completed successfully.");
    }
}