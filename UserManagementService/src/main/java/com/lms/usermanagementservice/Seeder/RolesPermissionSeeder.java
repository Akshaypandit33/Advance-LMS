package com.lms.usermanagementservice.Seeder;

import com.LMS.Constants.ACTIONS;
import com.LMS.Constants.DefaultRoles;

import com.lms.usermanagementservice.Model.Actions;
import com.lms.usermanagementservice.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RolesPermissionSeeder implements CommandLineRunner {
    private final RolesRepository rolesRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final ResourceRepository resourceRepository;
    private final ActionsRepository actionsRepository;

    private static final List<String> DEFAULT_ROLES = Arrays
            .stream(DefaultRoles.values())
            .map(DefaultRoles::name)
            .toList();



    @Override
    public void run(String... args) throws Exception {

        // step -1 add actions
        for(ACTIONS action : ACTIONS.values())
        {
            actionsRepository.findActionsByAction(action).orElseGet(
                    () ->  actionsRepository.save(Actions.builder()
                            .action(action)
                    .build()));
        }



    }
}
